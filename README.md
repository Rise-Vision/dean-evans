###dean-evans###
**Copyright © 2010 - May 22, 2014 Rise Vision Incorporated.**

Use of this software is governed by the GPLv3 license (reproduced in the LICENSE file).


Dean Evans (www.dea.com) provides room scheduling software. Using the Dean Evans API, the Gadget & app request data from the Dean Evans server to be displayed into a presentation. The Dean Evans EMS Data Service app is responsible for communicating with the Dean Evans EMS Server and then serving the data to Presentation via the Dean Evans Gadget.

**How to get started?** All source code for the Project is included in this repository and organized as follows:
* EMSInstall - Windows installer, will install Dean Evans EMS Data service (EMSService) on the machine
* EMSService - Java application, running Jetty server on port 8080. User can configure the EMS server url and access credentials
* EMSWeb - Java application, responsible for making SOAP connection to EMS server to get all bookings by date
* EMSGadget - Rise Gadget to display Dean Evans Events

**To develop on your local machine:**

To build EMSInstall, you will need NSIS 2.46 on your machine, NSIS 2.46 can be downloaded from, http://nsis.sourceforge.net/Download. Once installed, open the NSIS script compiler and open the file EMSInstall\setup.nsi from the repository.

EMSService and EMSWeb are Java Applications. As such, any IDE can be used, but requires JDK 1.6  or higher.

EMSGadget is a Gadget that, when embedded into HTML, captures data from the EMSWeb app and defines how the data is shown. 

**Dean Evans App Supported Functions, Parameters & Fields**

The Dean Evans EMS Data Service App supports only one function, GetAllBookings.
The following are required parameters for the GetAllBookings function:
* Username
* Password
* StartDate
* EndDate
* BuildingID

Currently, the Gadget will only display the following fields returned from the GetAllBookings function:

* Start Time
* End Time
* Event
* Room
* Room Description
* Contact

**How to configure on local machine**
1. Install the latest version of Java

2. Install the Dean Evans EMS Data Service app. Start the application and click “Configuration”.
3. Enter the EMS Service URL, Username & Password given to you by Dean Evans.

Note: There is no field to define the required fields of StartDate, EndDate or BuildingID. These variables are defined in the Gadget settings.

You can test your configuration by copying and pasting the URL below into the a browser on the same machine the app was installed on. You can define the Building ID, StartDate & EndDate in the URL. Simply edit the URL for the values you wish: http://127.0.0.1:8080/bookings?buildingId=1&startDate=2011-12-14T00:00:00&endDate=2011-12-14T23:59:59

The response should be a page with the returned fields in addition to actual data from the EMS server.

If you have any questions or problems please don't hesitate to join our lively and responsive community at http://community.risevision.com.

If you are looking for user documentation on Rise Vision please see http://www.risevision.com/help/users/


If you would like more information on developing applications for Rise Vision please visit http://www.risevision.com/help/developers/.


And if you are **considering contributing to this open source project**, our favourite option, we have 3 good reasons why we released this code under version 3 of the GNU General Public License, and we think they are 3 good reasons for why you should get involved too:

* Together we can make something far better than we could on our own.

* If you want to use our code to make something that is specific to you, and that doesn’t fit with what we want to do, we don’t want to get in your way. Take our code and make just what you need.

* We know that some of you nervous types worry about what happens if our company gets taken out in the zombie apocalypse. We get it, and neither one of us wants to deal with that delicate question of software escrow agreements for the “just in case we kick the bucket scenario”. No worries! We made it easy. No fuss, no cost, no lawyers! We published the software here. Have at it.


3 compelling reasons for why you should actively join our project.

Together we can make something better than either of us could on our own.

If you have something completely different in mind, no problem, take our code, fork it, and make what you need, but respect the open source movement, and our license, and keep it open.


Become a zombie crusader!


Are we missing something? Something could be better? Jump in, branch our code, make what you want, and send us a Pull Request. If it fits for both of us then of course we will accept it, maybe with a tweak or two, test it, and deploy it. If it doesn’t fit, no worries, just fork our code and create your own specialized application for your specific needs. Or, if you’re just feeling paranoid, download the code, and put it under your mattress.


**Either way, welcome to our project!**
