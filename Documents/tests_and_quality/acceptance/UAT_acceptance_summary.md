# UAT Summary Table 2026

## Short description of the overall results

- Total test cases: 11
- Leevi Laune: 11/11 passed
- Sakari Honkavaara: 10/11 passed, 1/11 failed
- Elias Rinne: 10/11 passed, 1/11 failed
- Current team-level status: 10/11 passed, 1/11 failed
  - Test failed due no up and running server
  - Test was successfully working with server running as intended

## UAT summary table

| Test case | Description | Leevi Laune | Sakari Honkavaara | Elias Rinne | Current overall status | Notes |
|---|---|---|---|---|---|---|
| AT-01 | The system must allow a teacher to log in successfully | Pass | Pass | Pass | Pass | Elias: Visibility was little bad due font size \| Sakari: A “show password” feature would improve usability |
| AT-02 | The system must allow a teacher to create and manage courses | Pass | Pass | Pass | Pass |  |
| AT-03 | The system must allow attendance to be marked as present or absent. | Pass | Pass | Pass | Pass |  |
| AT-04 | Course View Usability | Pass | Pass | Pass | Pass | Elias: Visibility was little bad due font size; Fontsize on select bar was bit too small |
| AT-05 | Attendance Update Reliability | Pass | Pass | Pass | Pass | Sakari: UI for updating attendance could be more clearer |
| AT-06 | Student login | Pass | Pass | Pass | Pass | Elias: Visibility was little bad due font size |
| AT-07 | QR Code Attendance | Pass | Fail | Fail | Fail | Leevi: qr code is a bit hard to read from laptop reading the qr from non integrated qr scanner wont redirect correctly \| Elias: Didnt open correctly due local testing \| Sakari: Camera access blocked (requires HTTPS). |
| AT-08 | Attendance Statistics | Pass | Pass | Pass | Pass | Elias: Barely pass, color sceme is little bit hard to read |
| AT-09 | Admin login | Pass | Pass | Pass | Pass | Elias: Visibility was little bad due font size \| Sakari: A “show password” feature would improve usability |
| AT-10 | User management | Pass | Pass | Pass | Pass | Elias: Selecting role was hard to read; Changing the role was hard to read |
| AT-11 | Verify that a teacher can view attendance statistics | Pass | Pass | Pass | Pass | Leevi: courses list has 1.5 courses only visible at once, user friendliness isnt good \| Elias: Color sceme might neeed adjustment for visibility \| Sakari: Styling/layout could be improved |
