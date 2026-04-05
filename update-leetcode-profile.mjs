#!/usr/bin/env node
/**
 * Updates stats.json with live LeetCode profile stats using LeetCode's public GraphQL endpoint,
 * and syncs a marked section in the root README.md.
 *
 * Usage:
 *   node update-leetcode-profile.mjs --username <leetcodeUsername>
 */

import fs from 'node:fs/promises';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const GRAPHQL_URL = 'https://leetcode.com/graphql';
const README_DEFAULT = 'README.md';
const REPO_ROOT = path.dirname(fileURLToPath(import.meta.url));

function parseArgs(argv) {
  const args = { username: undefined, file: 'stats.json', readme: README_DEFAULT };
  for (let i = 0; i < argv.length; i++) {
    const a = argv[i];
    if (a === '--username' || a === '-u') {
      args.username = argv[i + 1];
      i++;
    } else if (a === '--file' || a === '-f') {
      args.file = argv[i + 1];
      i++;
    } else if (a === '--readme' || a === '-r') {
      args.readme = argv[i + 1];
      i++;
    } else if (a === '--no-readme') {
      args.readme = null;
    } else if (a === '--help' || a === '-h') {
      args.help = true;
    }
  }
  return args;
}

function requireNonEmpty(value, label) {
  if (!value || typeof value !== 'string' || value.trim() === '') {
    throw new Error(`Missing ${label}. Try: --${label} <value>`);
  }
  return value.trim();
}

function formatProfileMarkdown(profile) {
  const ranking = profile.ranking ?? '—';
  const reputation = profile.reputation ?? '—';
  const stars = profile.starRating ?? '—';

  return [
    `**Username:** ${profile.username}  `,
    `**Solved:** ${profile.solved} (Easy ${profile.easy} / Medium ${profile.medium} / Hard ${profile.hard})  `,
    `**Ranking:** ${ranking}  `,
    `**Reputation:** ${reputation}  `,
    `**Star Rating:** ${stars}  `,
    `**Last Updated:** ${profile.fetchedAt}`,
  ].join('\n');
}

async function updateReadmeBlock(readmePath, profileMarkdown) {
  const start = '<!-- LEETCODE_PROFILE:START -->';
  const end = '<!-- LEETCODE_PROFILE:END -->';

  const abs = path.resolve(REPO_ROOT, readmePath);
  const original = await fs.readFile(abs, 'utf8');

  const startIdx = original.indexOf(start);
  const endIdx = original.indexOf(end);

  if (startIdx === -1 || endIdx === -1 || endIdx < startIdx) {
    throw new Error(
      `README markers not found in ${path.relative(REPO_ROOT, abs)}. Add:\n${start}\n...\n${end}`
    );
  }

  const before = original.slice(0, startIdx + start.length);
  const after = original.slice(endIdx);
  const next = `${before}\n${profileMarkdown}\n${after}`;

  if (next !== original) {
    await fs.writeFile(abs, next, 'utf8');
  }
}

async function fetchLeetCodeProfile(username) {
  const query = `
    query userProfile($username: String!) {
      matchedUser(username: $username) {
        username
        submitStats {
          acSubmissionNum {
            difficulty
            count
            submissions
          }
        }
        profile {
          ranking
          reputation
          starRating
        }
      }
    }
  `;

  const res = await fetch(GRAPHQL_URL, {
    method: 'POST',
    headers: {
      'content-type': 'application/json',
      accept: 'application/json',
    },
    body: JSON.stringify({ query, variables: { username } }),
  });

  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(
      `LeetCode request failed: HTTP ${res.status} ${res.statusText}${text ? ` - ${text.slice(0, 200)}` : ''}`
    );
  }

  const json = await res.json();
  if (json?.errors?.length) {
    const msg = json.errors.map((e) => e.message).join('; ');
    throw new Error(`LeetCode GraphQL error: ${msg}`);
  }

  const user = json?.data?.matchedUser;
  if (!user) {
    throw new Error(`User not found on LeetCode: ${username}`);
  }

  const ac = user?.submitStats?.acSubmissionNum ?? [];
  const byDiff = Object.fromEntries(
    ac
      .filter((x) => x?.difficulty)
      .map((x) => [String(x.difficulty).toLowerCase(), Number(x.count ?? 0)])
  );

  const solved = Number(byDiff.all ?? 0);
  const easy = Number(byDiff.easy ?? 0);
  const medium = Number(byDiff.medium ?? 0);
  const hard = Number(byDiff.hard ?? 0);

  return {
    username: user.username,
    solved,
    easy,
    medium,
    hard,
    ranking: user?.profile?.ranking ?? null,
    reputation: user?.profile?.reputation ?? null,
    starRating: user?.profile?.starRating ?? null,
    fetchedAt: new Date().toISOString(),
  };
}

async function readJson(filePath) {
  const txt = await fs.readFile(filePath, 'utf8');
  return JSON.parse(txt);
}

async function writeJson(filePath, data) {
  const txt = JSON.stringify(data, null, 2) + '\n';
  await fs.writeFile(filePath, txt, 'utf8');
}

async function main() {
  const args = parseArgs(process.argv.slice(2));
  if (args.help) {
    process.stdout.write(
      [
        'Update stats.json with LeetCode profile stats',
        '',
        'Usage:',
        '  node update-leetcode-profile.mjs --username <leetcodeUsername>',
        '',
        'Options:',
        '  -u, --username   LeetCode username (required)',
        '  -f, --file       JSON file to update (default: stats.json)',
        '  -r, --readme     README file to update (default: README.md)',
        '      --no-readme  Skip README update',
      ].join('\n') + '\n'
    );
    return;
  }

  const username = requireNonEmpty(args.username, 'username');
  const statsFile = path.resolve(REPO_ROOT, args.file ?? 'stats.json');
  const readmeFile = args.readme;

  const profile = await fetchLeetCodeProfile(username);

  let stats;
  try {
    stats = await readJson(statsFile);
  } catch {
    stats = {};
  }

  if (!stats.leetcode || typeof stats.leetcode !== 'object') {
    stats.leetcode = {};
  }

  stats.leetcode.profile = profile;

  await writeJson(statsFile, stats);

  if (readmeFile) {
    const md = formatProfileMarkdown(profile);
    await updateReadmeBlock(readmeFile, md);
  }

  process.stdout.write(
    `Updated ${path.relative(REPO_ROOT, statsFile)} for ${profile.username}: solved=${profile.solved} (E${profile.easy}/M${profile.medium}/H${profile.hard})\n`
  );
}

main().catch((err) => {
  process.stderr.write(String(err?.message ?? err) + '\n');
  process.exitCode = 1;
});
