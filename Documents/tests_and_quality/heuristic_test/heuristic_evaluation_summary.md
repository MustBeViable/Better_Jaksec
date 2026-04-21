# Heuristic Evaluation Summary

## Project Information

- **Project name:** Better_Jaksec
- **Team:** Elias Rinne, Leevi Laune, Sakari Honkavaara
- **Date:** 21.4.2026
- **Version / Sprint:** 1.0 / Sprint 7
- **Evaluated flow / page:** Combined team summary
- **Repository:** https://github.com/MustBeViable/Better_Jaksec

## Severity aggregation rule

This summary combines the three individual heuristic evaluations into one team-level summary.

Severity was selected using the agreed rule:
- when two evaluators gave the same lower value and one gave a higher value, the lower value was used
- when all three values differed, the middle value was used
- if an issue was missing from an individual report, it was treated as **0** for aggregation

## Summary Table

| No | Heuristic | Description of the Issue | Screenshot | Severity | Suggested Improvement |
|---|---|---|---|---:|---|
| 1 | H1-1: Simple & natural dialog | Course and main views are not immediately clear. Key actions, page purpose, and course-management actions can be hard to understand at first. | [course_example.png](https://github.com/MustBeViable/Better_Jaksec/blob/main/Documents/tests_and_quality/heuristic_example_screenshots/course_example.png) | 2 | Clarify page purpose with stronger hierarchy, clearer primary actions, and short guidance for course-management tasks. |
| 2 | H1-2: Speak the users’ language | Some labels and interface wording are unclear or misleading for teachers and first-time users. | [main_screen_example.png](https://github.com/MustBeViable/Better_Jaksec/blob/main/Documents/tests_and_quality/heuristic_example_screenshots/main_screen_example.png) | 1 | Use clearer, more descriptive labels and action names that match users’ expectations. |
| 3 | H1-3: Minimize users’ memory load | Users must remember too much. Attendance marking requires a long path, the main page gives little guidance, and the login form lacks a show-password option. | N/A | 2 | Reduce memory load with clearer entry points, simpler task flows, and a show-password toggle. |
| 4 | H1-4: Consistency | Visual and informational consistency varies between pages. Some option boxes, QR views, layouts, and lesson details do not match the rest of the interface. | [user_manage_example.png](https://github.com/MustBeViable/Better_Jaksec/blob/main/Documents/tests_and_quality/heuristic_example_screenshots/user_manage_example.png) | 1 | Apply one shared design system for layouts, component styling, and key information placement across views. |
| 5 | H1-5: Feedback | System feedback is inconsistent. Some actions lack clear success or failure messages, attendance state can be lost on refresh, and QR scanning feedback is delayed or unclear. | N/A | 2 | Add immediate status feedback, confirm saved changes clearly, and preserve or autosave attendance state. |
| 6 | H1-6: Clearly marked exits | Exit and next-step paths are not always obvious. Some flows lack prominent cancel or back options, and the QR reader especially needs a clearer way out. | N/A | 2 | Make back, cancel, close, and next-step actions visible and consistent in every flow. |
| 7 | H1-7: Shortcuts | Frequent teacher tasks require repetitive manual work, and there are no strong shortcuts for experienced users. | N/A | 2 | Add efficiency features such as **Mark all present**, quicker access paths, and task shortcuts. |
| 8 | H1-8: Precise & constructive error messages | Error messages are often too generic and do not clearly explain what went wrong, which field caused the issue, or how the user can fix it. | [update_failed_example.png](https://github.com/MustBeViable/Better_Jaksec/blob/main/Documents/tests_and_quality/heuristic_example_screenshots/update_failed_example.png) | 2 | Show precise, field-level error messages with clear correction guidance. |
| 9 | H1-9: Prevent errors | The interface does not prevent several common mistakes. Destructive actions may happen without confirmation, attendance state may be lost, and some problems are only discovered after the fact. | [update_failed_example.png](https://github.com/MustBeViable/Better_Jaksec/blob/main/Documents/tests_and_quality/heuristic_example_screenshots/update_failed_example.png) | 3 | Prevent mistakes with confirmations, autosave or state persistence, validation before submission, and undo where possible. |
| 10 | H1-10: Help and documentation | The system provides very little built-in help for new users. There is no clear FAQ, help view, or task guidance. | N/A | 1 | Add a help section, onboarding guidance, tooltips, or short instructions for key features. |

## Notes

- This file is intended for GitHub project documentation.
- Screenshot links point to the repository folder: `Documents/tests_and_quality/heuristic_example_screenshots`.
