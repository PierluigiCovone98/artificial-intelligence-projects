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
echo -e "\n${PURPLE}Artificial Intelligence - Pierluigi Covone${NC}\n"

# 1. Get the project root directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
SRC_DIR="$PROJECT_ROOT/src"
OUT_DIR="$PROJECT_ROOT/out"

# 2. Check that src/ exists.
if [[ ! -d "$SRC_DIR" ]]; then
    echo -e "${log_err}Source directory '${SRC_DIR}' not found. Exiting.\n"
    exit 1
fi


# 3. Compile if needed.
echo -e "${log_info}Checking compilation..."

if [[ -d "$OUT_DIR" ]] && [[ -n "$(ls -A "$OUT_DIR" 2>/dev/null)" ]]; then
    echo -e "${log_warn}Project already compiled. Skipping compilation.\n"
else
    echo -e "${log_info}Compiling project..."

    mkdir -p "$OUT_DIR"

    ORIGINAL_DIR="$(pwd)"
    cd "$PROJECT_ROOT"

    find src -name "*.java" > .sources.txt

    if ! javac -d out @.sources.txt; then
        echo -e "${log_err}Compilation failed. Exiting.\n"
        rm -rf out
        rm -f .sources.txt
        cd "$ORIGINAL_DIR"
        exit 1
    fi

    rm -f .sources.txt
    cd "$ORIGINAL_DIR"

    echo -e "${log_ok}Compilation successful.\n"
fi


# 4. Ready message.
echo -e "${GREEN}========================================${NC}"
echo -e "${log_ok}Project ready."
echo -e "${GREEN}========================================${NC}\n"


# === Main menu loop ===
while true; do

    # 5. Category selection.
    echo -e "${BLUE}Problem categories:${NC}\n"
    echo -e "  ${GREEN}0${NC}) Exit"
    echo -e "  ${GREEN}1${NC}) State Space Exploration Problems"
    echo ""

    while true; do
        read -rp "Select an option [0-1]: " category

        if [[ "$category" =~ ^[0-9]+$ ]] && (( category >= 0 && category <= 1 )); then
            break
        fi

        echo -e "${log_err}Invalid choice. Please enter 0 or 1."
    done

    # Exit
    if [[ "$category" == "0" ]]; then
        echo -e "\n${log_ok}Bye!\n"
        exit 0
    fi


    # 6. Handle selected category.
    case "$category" in

        1)
            # === State Space Exploration Problems — test loop ===
            while true; do

                echo -e "\n${PURPLE}==> State Space Exploration Problems${NC}\n"

                echo -e "${BLUE}Available tests:${NC}\n"
                echo -e "  ${GREEN}0${NC}) Back"
                echo -e "  ${GREEN}1${NC}) Romania Trip (to Bucharest)"
                echo -e "  ${GREEN}2${NC}) HP 2D Protein Folding"
                echo ""

                while true; do
                    read -rp "Select a test [0-2]: " test_choice

                    if [[ "$test_choice" =~ ^[0-9]+$ ]] && (( test_choice >= 0 && test_choice <= 2 )); then
                        break
                    fi

                    echo -e "${log_err}Invalid choice. Please enter a number between 0 and 2."
                done

                # Back to main menu
                if [[ "$test_choice" == "0" ]]; then
                    echo ""
                    break
                fi

                case "$test_choice" in

                    1)
                        # === Romania Trip ===
                        echo -e "\n${PURPLE}Romania Trip (to Bucharest)${NC}\n"
                        echo -e "${log_info}Running test...\n"
                        echo -e "${GREEN}----------------------------------------${NC}\n"

                        java -cp "$OUT_DIR" it.uniroma1.ai.problems.romaniatrip.TestRomaniaTripToBucharestProblem

                        echo -e "\n${GREEN}----------------------------------------${NC}"
                        echo -e "${log_ok}Test completed.\n"
                        ;;

                    2)
                        # === HP 2D Protein Folding ===
                        echo -e "\n${PURPLE}HP 2D Protein Folding${NC}\n"

                        while true; do
                            read -rp "Insert protein sequence (H/P only): " protein

                            if [[ -z "$protein" ]]; then
                                echo -e "${log_err}Protein sequence cannot be empty."
                                continue
                            fi

                            if [[ ! "$protein" =~ ^[HP]+$ ]]; then
                                echo -e "${log_err}Invalid sequence. Use only 'H' and 'P' characters."
                                continue
                            fi

                            if [[ ${#protein} -lt 2 ]]; then
                                echo -e "${log_err}Protein must have at least 2 amino acids."
                                continue
                            fi

                            break
                        done

                        echo -e "\n${log_ok}Valid protein: ${PURPLE}${protein}${NC} (length: ${#protein})\n"
                        echo -e "${log_info}Running test...\n"
                        echo -e "${GREEN}----------------------------------------${NC}\n"

                        java -cp "$OUT_DIR" it.uniroma1.ai.problems.proteinfolding.TestProteinFolding "$protein"

                        echo -e "\n${GREEN}----------------------------------------${NC}"
                        echo -e "${log_ok}Test completed.\n"
                        ;;

                esac

                # After test: ask what to do
                echo -e "${BLUE}What next?${NC}\n"
                echo -e "  ${GREEN}0${NC}) Exit"
                echo -e "  ${GREEN}1${NC}) Run another test"
                echo -e "  ${GREEN}2${NC}) Back to main menu"
                echo ""

                while true; do
                    read -rp "Select an option [0-2]: " next

                    if [[ "$next" =~ ^[0-9]+$ ]] && (( next >= 0 && next <= 2 )); then
                        break
                    fi

                    echo -e "${log_err}Invalid choice. Please enter 0, 1, or 2."
                done

                if [[ "$next" == "0" ]]; then
                    echo -e "\n${log_ok}Bye!\n"
                    exit 0
                elif [[ "$next" == "2" ]]; then
                    echo ""
                    break
                fi
                # next == 1: loop continues, shows test menu again

            done
            ;;

    esac

done