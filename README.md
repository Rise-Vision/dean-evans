# dean-evans

## Introduction

Dean Evans (www.dea.com) provides room scheduling software. Using the Dean Evans API, the Gadget & app request data from the Dean Evans server to be displayed into a presentation. The Dean Evans EMS Data Service app is responsible for communicating with the Dean Evans EMS Server and then serving the data to Presentation via the Dean Evans Gadget.

dean-evans projects is organised as follow:

 - EMSInstall - Windows installer, will install Dean Evans EMS Data service (EMSService) on the machine
 - EMSService - Java application, running Jetty server on port 8080. User can configure the EMS server url and access credentials
 - EMSWeb - Java application, responsible for making SOAP connection to EMS server to get all bookings by date
 - EMSGadget - Rise Gadget to display Dean Evans Events

## Built With
- [Eclipse IDE for Java EE Developers](http://www.eclipse.org/downloads/)
- [Java 1.7](http://www.oracle.com/technetwork/java/javaee/downloads/java-ee-sdk-7-downloads-1956236.html)
- [NSIS 2.4](http://nsis.sourceforge.net/Download)
- *jetty* 

## Development

### Dependencies
* Requires Java SDK which can be downloade and installed via [Oracle Download Page](http://www.oracle.com/technetwork/java/javaee/downloads/java-ee-sdk-7-downloads-1956236.html")
* Requires NSIS 2.46 [NSIS Download Page](http://nsis.sourceforge.net/Download")
* Required Jetty libraries are included in repository

#### EMS Web and EMS Service

To build Java projects, you will need Eclipse on your machine. In Eclipse create a new workspace. Import the EMSWeb from /EMSWeb and EMS Service from /EMSService into Eclipse.

1. Select File menu
2. Select Import
3. Under General, select "Existing Projects into Workspace"
4. Select the root directory for whichever project you want to import. 

#### To Debug EMS project in Eclipse

1. Right click on EMSService project in project Explorer
2. Select Debug as
3. Select Java Application
4. Select MainForm - com.risedisplay.ems.ui
5. Select OK

#### When you are ready, build, export the projects and create Installation Package

##### Export EMSService

1. In Eclipse, Right Click on EMSService project in project Explorer
2. Click Export
3. From the Java option, select "Runnable Jar File"
4. Under Launch Configuration choose "MainForm - EMSService"
5. Under export destination enter "EMSInstall\Files\RiseDeanEvansEMSService.jar"
6. Under Library handling choose "Package required libraries into generated JAR"

##### Export EMSWeb

 1. In Eclipse, Right Click on EMSWeb project in project Explorer
 2. Click Export and click "WAR file"
 3. Under Web project choose "EMSWeb"
 4. under Destination enter "EMSInstall\Files\EMSWeb.war"
 5. Using any unzip utility, extract EMSWeb.war to 
 6. "EMSInstall\Files\Web" should contain following items
	 - "META-INF" folder
	 - "WEB-INF" folder and
	 - "emsservice.conf" file
 7. Delete "EMSInstall\Files\EMSWeb.war"

##### Create Installation Package

 1. Run EMSInstall\setup.nsi in NSIS to create "RiseDeanEvansEMSServiceInstall.exe" executable

### Run Local
Run "RiseDeanEvansEMSServiceInstall.exe" to install the EMS on your local machine.

### Configuration

####EMS Service Configuration
 1. Start the application
 2. Click on Configuration
 3. Enter the EMS Service URL, Username & Password
 4. Test the app by copying and pasting this [URL](http://www.google.com/url?q=http%3A%2F%2F127.0.0.1%3A8080%2Fbookings%3FbuildingId%3D1%26startDate%3D2011-12-14T00%3A00%3A00%26endDate%3D2011-12-14T23%3A59%3A59&sa=D&sntz=1&usg=AFQjCNFEu1BFFTBGlRWmk_usi3RdTlipJg) into the a browser on the same machine the app is running on. You can define the Building ID, StartDate & EndDate in the URL. Simply edit the URL for the values you wish. The response should be a page with the returned fields in addition to actual data from the EMS server, looking like [this](http://www.google.com/url?q=http%3A%2F%2Fscreencast.com%2Ft%2FHDnwj5JaE&sa=D&sntz=1&usg=AFQjCNHOxZiU0p3bKUQ-VlD3l3k2TQicLg)

####Rise Dean Evans EMS Data Service Gadget Configuration:
Gadget XML can be stored at any online repository. Gadget can be added in the Company or directly to the Presentation as a Gadget using URL.

 - Add the Gadget to a Presentation.
 - Specify the building ID, Number of Days and other options in the Gadget, like in [this example](http://www.google.com/url?q=http%3A%2F%2Fscreencast.com%2Ft%2FAq7toiXxA&sa=D&sntz=1&usg=AFQjCNFA0dO9EO_edcXocB86nli0re4tpw).
 - The Gadget should populate with data delivered from the App.

## Submitting Issues 
If you encounter problems or find defects we really want to hear about them. If you could take the time to add them as issues to this Repository it would be most appreciated. When reporting issues please use the following format where applicable:

**Reproduction Steps**

1. did this
2. then that
3. followed by this (screenshots / video captures always help)

**Expected Results**

What you expected to happen.

**Actual Results**

What actually happened. (screenshots / video captures always help)

## Contributing
All contributions are greatly appreciated and welcome! If you would first like to sound out your contribution ideas please post your thoughts to our [community](http://community.risevision.com), otherwise submit a pull request and we will do our best to incorporate it


## Resources
If you have any questions or problems please don't hesitate to join our lively and responsive community at http://community.risevision.com.

If you are looking for user documentation on Rise Vision please see http://www.risevision.com/help/users/

If you would like more information on developing applications for Rise Vision please visit http://www.risevision.com/help/developers/. 




**Facilitator**

[Alan Clayton](https://github.com/alanclayton "Alan Clayton")