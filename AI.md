# AI Tool Usage and Observations

## Refactoring: SLAP (Single Level of Abstraction Principle) Application

**Date:** 19 February 2026

### Tool Used
**GitHub Copilot** - Used for code refactoring and extraction suggestions

### Task Summary
Applied SLAP to refactor the `Ferdi.java` class, addressing the lengthy `run()` and `getResponse(String)` methods that contained repetitive command handling logic.

### What Was Done

#### 1. CLI Mode Refactoring (`run()` method)
- **Before:** 130+ lines with nested if-else blocks handling 8 command types
- **After:** Extracted into focused helper methods:
  - `handleCommand(String)` - main dispatcher
  - `handleListCommand()` - list handling
  - `handleAddTaskCommand(String, String)` - unified todo/deadline/event creation (uses Java switch expression)
  - `handleMarkCommand(String, boolean)` - unified mark/unmark logic
  - `handleFindCommand(String)` - find operation
  - `handleUpdateCommand(String)` - update operation
- **Result:** `run()` method reduced to ~15 lines of clear, scannable code

#### 2. Chat Mode Refactoring (`getResponse(String)` method)
- **Before:** 150+ lines with StringBuilder and nested if-else blocks
- **After:** Refactored to use Java switch expressions returning individual response methods:
  - `getListResponse()` - generates list output
  - `getAddTaskResponse(String, String)` - unified task creation response
  - `getMarkResponse(String, boolean)` - unified mark/unmark response
  - `getFindResponse(String)` - search results formatting
  - `getUpdateResponse(String)` - update confirmation
  - `getUnknownCommandResponse(String)` - error handling
- **Result:** `getResponse()` reduced to ~20 lines using modern switch expressions

#### 3. Code Quality Improvements
- Eliminated duplicate command handling logic between CLI and chat modes
- Used Java 12+ switch expressions for cleaner, more readable dispatch logic
- Maintained consistent error handling patterns
- Added comprehensive Javadoc for all new helper methods
- Improved single responsibility principle for each method

### What Worked Well
- Switch expressions made the code more readable than if-else chains
- Helper methods have single, clear responsibilities
- Duplicated logic between CLI and chat modes was successfully identified and managed
- Method extraction improved testability (helper methods can now be tested independently)
- Added documentation clarity with focused Javadoc comments

### What Didn't Work As Expected
- Initial approach to completely unify CLI and chat handler logic was too aggressive
- Rather than the initial method of using if branches, now it uses switch case branching due to lack of context during prompting.
- Solution: Created separate `handle*` and `get*` method pairs to respect the different return patterns (void vs String)

### Time Impact
- **Estimated manual refactoring time:** 45-60 minutes of careful code restructuring
- **Actual time with AI assistance:** ~10-15 minutes including verification
- **Time saved:** ~35-45 minutes (75% reduction)