# UAT Testing Summary – What We Learned

## Overview
During the UAT testing, we observed how real users interacted with the service and what kinds of usability issues, strengths, and confusions appeared during normal use. The testing gave useful insight into how different kinds of users understand the interface, find important features, and interpret the purpose of the site.

## Main Findings

### 1. First impressions strongly affect usability
Users formed quick opinions about the site based on its visual style and structure. Some users felt that:
- the site looked too much like something made for teachers or like Wilma
- the page felt visually unappealing
- the font was too pale
- the contrast in text and images was too weak
- some sections looked like advertisements or newspaper articles instead of navigation elements

This shows that visual design has a major impact on how trustworthy, clear, and pleasant the system feels.

### 2. Important content was not always easy to find
Although some users found the correct page or feature quickly, others had clear difficulties in navigation. Observed problems included:
- not finding the page easily
- going to the wrong page
- not finding card-based identification
- not reaching the About Us page without guidance
- difficulty locating some important features even when they were present

This suggests that navigation and page hierarchy should be made clearer, especially for first-time users.

### 3. Users interpret the interface in different ways
Some users were experienced browsers and moved quickly through the interface, while others felt overwhelmed or uncertain. We noticed that:
- one user seemed very comfortable using websites and may even have been more skilled than the average target user
- another user felt overwhelmed by the page
- some users needed guidance even when the correct feature was available
- some users were unsure whether activation was needed before continuing

This means the product should support both experienced and less experienced users more clearly.

### 4. The service purpose was not always immediately clear
Some users misunderstood what the service was for. For example:
- one user thought the page was meant for teachers
- some page content was interpreted as descriptive text rather than useful navigation
- users did not always understand where to continue next

This indicates that the landing page and key sections should communicate the purpose of the service more directly.

### 5. Some parts worked well
Despite the usability issues, the testing also showed positive results:
- some users found the page easily
- one user found card authentication immediately
- one user quickly found where card testing could be done
- users were able to find information fairly easily in some parts of the site
- one user found the mission section, even though there was a bug in its link

These results show that some content and navigation choices already work and can be built on further.

### 6. Bugs and technical issues affected the experience
Testing also revealed concrete implementation issues:
- the “Mission” link led to a 404 page
- some important content was not accessible without help
- users ended up in incorrect places during the process

These issues reduce confidence in the system and should be fixed before further release or testing.

## What We Learned from UAT
Based on the testing, we learned that:
- users do not always navigate as expected, even when the interface seems clear to developers
- visual clarity and contrast matter a lot for usability
- first-time users need stronger guidance and more obvious navigation
- key features must be easier to notice
- page structure and wording should better support users who are unsure or overwhelmed
- even small bugs, such as broken links, have a strong negative effect on trust
- experienced users and less experienced users may have very different experiences, so the interface should work for both

## Key Improvement Areas
Based on the UAT results, the most important areas for improvement are:
- improve visual contrast in text and images
- make important functions easier to find
- clarify navigation and page hierarchy
- improve the visibility of the About Us and card identification sections
- make the service purpose clearer on the first page
- fix the 404 bug in the Mission link
- reduce the overwhelming feeling by simplifying the layout and guiding the user more clearly

## Conclusion
The UAT testing was valuable because it revealed both usability strengths and important weaknesses that were not obvious from a developer perspective. The results show that the service has useful content and some working interaction paths, but it still needs improvements in navigation, clarity, accessibility, and visual design. These findings help guide the next iteration of development and make the service easier and more pleasant to use.