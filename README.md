HOW TO RUN THE PROGRAM:

1. Install Maven
	-go to https://maven.apache.org/install.html
		-setup a JDK installation if you do not have one
	-follow on screen instructions
	-when installing Maven, check the box the adds Maven to your PATH variable



2. Run program from a shell
	1. Open cmd
	2. change directory to demo (the project folder)
	3. run 'mvn clean javafx:run'



HOW IT WORKS:
	1. The program starts in App.java, An instance of the GUI is created and initialized with data from log.txt
	2. When the GUI's start button is pressed. A Monitor thread is deployed which scrapes the specified Amazon page every X hours (X is specified in the GUI).
	3. Each loop of the Monitor compares the scraped price with the last price recorded in the log. If the new price is cheaper, the notification routine is called.
		The notification thread causes a popup or delivers an email based on the checked button in the GUI. The notification is logged with a timestamp.
		The thread logs the scraped price with a time stamp after the comparison logic.



CHALLENGES:


1. Scraping: Ironically the easiest and least time-consuming part of the challenge
	Utilized JSoup to grab the HTML document from a provided Amazon link.
        I used the power of inspect element on Amazon's ipad page to acquire the specific class ids to scrape. These values are uniform across
	Amazon item pages. That was all.
	

2. Automation: The bulk of the issues stemmed from here
	To achieve automated scraping, I used the sleep command to add a delay inbetween check loops. However, this caused the program to effectively "freeze"
	between loops which prompted Windows to repeatedly ask to kill the program. To get around this, I created a threaded class "Monitor" to contain the delay and re-scraping logic.
	This solved the freezing issue, but returning the scraped value to the GUI to update the price label was impossible with the default JavaFX setup as the 
	GUIcontroller instance could not be accessed from other classes. To remedy this, I had to replace the standard static implentation of the GUI with an instantiated
	one. This way, the instance could be referenced from my thread directly.
	This still wasn't the end of it, however. Accessing JavaFX controllers from a different thread causes an exception. After much troubleshooting and research I found
	out how to "jump" to the JavaFX thread from my Monitor's seperate thread, circumventing the issue.


3. Notfications:
	I utilized the Jakarta package to send emails using Mailtrap. This came with an issue, you need to have a web domain/email server to send emails to anyone except yourself using Mailtrap.
	Since I do not own any web domains or email servers, I changed my approach; Gmail. I attempted to utilize Google's API to send emails from a dummy gmail, problem: The imports 
	required to use the code are not all available in Maven's repository, so I was unable to compile Google's email code.
	As the Gmail solution felt unviable in my time frame, I went back to Mailtrap for a band-aid demo-only solution.
	I made a new Mailtrap account using a dummy gmail and I freely give out the name and password information in the code. 
	Thus, the email notification routine is effectively available to anyone!
	If I were to posess a web domain, it would be trivial to update the program to send email notifications to a user specified email.




ADDITIONAL FEATURES:
	Notification method and delay between price scrapings can be easily configured with the simple GUI.
	Notifications provide a direct link to the item the program is configured to monitor.
	Monitoring can be stopped and started with one push of a button.

POSSIBLE EXTENSIONS:
	Text message notifications: After my experience with implementing Email, I figured setting up text notifications would also require a great number of external services that
		I would need to configure. Given my current timeframe I elected to forgo this for now.
	Configurable email notification: If I possessed my own web domain, it would be a trivial matter to allow the user to send notification emails to any address they specify.
	Configurable item to monitor: It would be simple to add a text box where a user could provide the URL of any Amazon item to monitor. The code is already general enough to
		work on any Amazon item. 



TESTING:
	Scraping: I initially hooked up the GUI to scrape the price whenever you hit the start button and print the result. Once I moved to the automation phase, I tested the
			monitor loop by having it run every X seconds (instead of every X hours), log the result, and update the GUI. 

	Logging: Inserted code into the monitor loop to log results. Compared the log entries to the printed data to to ensure every event was recorded accurately.

	Data processing: To test the automated scraping -> notification loop, I needed the scraped price to drop from the previous known price. Since the price of the iPad did not
			fluctuate at any point during the development time, I inserted fake log entries with artificially high prices to trip the "lower price detected" scenario when the
			monitor loop ran.

	Notifications: I tested the notifcation routine by repeating the Data Processing tests after I added the notification behaviours. In addition, I added a button to the GUI that triggers
			  a test notification based on what radio button the GUI has selected (popup/email). I have left the button in the demo for your testing convenience.

	Portability: To ensure the program can be downloaded and ran on other machines. I uploaded the project on GitHub and downloaded it on my other computer that has no dev tools installed on it.
			The steps I have provided in the HOW TO section were used to run the program on said machine.


