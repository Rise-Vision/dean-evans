dean-evans
==========

About
=====
Dean Evans (www.dea.com) provides room scheduling software. Using the Dean Evans API, this Gadget & app request data from the Dean Evans server to be displayed into a presentation. The Dean Evans EMS Data Service app is responsible for communicating with the Dean Evans EMS Server and then serving the data to Presentation via the Dean Evans Gadget.

Requirements
============
- Rise Dean Evans EMS Data Service app installed on the same machine running Rise Vision Player.
- Latest version of Java installed
- A Dean Evans EMS server running the EMS API interface
- Dean Evans Gadget stored somewhere that is accesible via URL. You can download the Gadget from here: http://c752760.r60.cf2.rackcdn.com/dea.xml

App Supported Functions, Parameters & Fields
============================================
The Dean Evans EMS Data Service App supports only one function, GetAllBookings. 

The following are required parameters for the GetAllBookings function:
- Username
- Password
- StartDate
- EndDate
- BuildingID

Currently, the Gadget will only display the following fields returned from the GetAllBookings function:
- Start Time
- End Time
- Event
- Room
- Room Description
- Contact

Local App Configuration
=======================
1. Install the latest version of Java
2. Install the Dean Evans EMS Data Service app. Start the application and click “Configuration”.
3. Enter the EMS Service URL, Username & Password given to you by Dean Evans.

Note: There is no field to define the required fields of StartDate, EndDate or BuildingID. These variables are defined in the Gadget settings.

You can test your configuration by copying and pasting the URL below into the a browser on the same machine the app was installed on. You can define the Building ID, StartDate & EndDate in the URL. Simply edit the URL for the values you wish:
http://127.0.0.1:8080/bookings?buildingId=1&startDate=2011-12-14T00:00:00&endDate=2011-12-14T23:59:59

The response should be a page with the returned fields in addition to actual data from the EMS server.
