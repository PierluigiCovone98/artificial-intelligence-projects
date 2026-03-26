#!/usr/bin/env bash
set -euo pipefail

# === Colors & Log Shortcuts ===
RED='\033[31m'
GREEN='\033[32m'
YELLOW='\033[33m'
BLUE='\033[34m'
PURPLE='\033[35m'
NC='\033[0m'

log_info="${BLUE}[Log]${NC}\t"
log_ok="${GREEN}[Ok]${NC}\t"
log_warn="${YELLOW}[Warn]${NC}\t"
log_err="${RED}[Err]${NC}\t"

# === Starting Message ===
echo -e "\n${PURPLE}Artificial Intelligence - Reset${NC}\n"

# 1. Get the project root directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
OUT_DIR="$PROJECT_ROOT/out"

# 2. Check if out/ exists
echo -e "${log_info}Checking compiled output..."

if [[ ! -d "$OUT_DIR" ]]; then
    echo -e "${log_warn}Nothing to clean. Directory 'out/' does not exist.\n"
    exit 0
fi

# 3. Remove out/
rm -rf "$OUT_DIR"
echo -e "${log_ok}Directory 'out/' removed. Project will be recompiled on next run.\n"