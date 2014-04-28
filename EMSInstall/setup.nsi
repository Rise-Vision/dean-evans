################################################################################
# Rise Dean Evans EMS Data Service Installer                                   #
# Copyright (C) 2011 Rise Display Inc                                          #
################################################################################

# General Symbol Definitions
!define VERSION 1.1.0.3
!define COMPANY "Rise Display Inc."
!define URL http://www.risedisplay.com

Name "Rise Dean Evans EMS Data Service"
Caption "Rise Dean Evans EMS Data Service"
BrandingText "Rise Dean Evans EMS Data Service v${VERSION}"

# MUI Symbol Definitions
!define MUI_ICON Files\RiseDeanEvansEMSService.ico
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_UNICON Files\RiseDeanEvansEMSService.ico
!define MUI_UNFINISHPAGE_NOAUTOCLOSE

!define MULTIUSER_EXECUTIONLEVEL Standard
!define MULTIUSER_NOUNINSTALL
!define MULTIUSER_INSTALLMODE_COMMANDLINE
!define MULTIUSER_INSTALLMODE_INSTDIR "RiseDeanEvansEMSService"

# Included files
!include Sections.nsh
!include MUI2.nsh
!include MultiUser.nsh

# Installer pages
!insertmacro MUI_PAGE_INSTFILES

# Installer languages
!insertmacro MUI_LANGUAGE English

# Installer attributes
OutFile RiseDeanEvansEMSServiceInstall.exe
InstallDir $LOCALAPPDATA\RiseDeanEvansEMSService
Icon "Files\RiseDeanEvansEMSService.ico"
InstallColors /windows
CRCCheck on
XPStyle on
ShowInstDetails show
VIProductVersion 1.1.0.3
VIAddVersionKey ProductName "Rise Dean Evans EMS Data Service"
VIAddVersionKey ProductVersion "${VERSION}"
VIAddVersionKey CompanyName "${COMPANY}"
VIAddVersionKey CompanyWebsite "${URL}"
VIAddVersionKey FileVersion "${VERSION}"
VIAddVersionKey FileDescription ""
VIAddVersionKey LegalCopyright "Copyright (C) 2011 Rise Display Inc"

# Sub Captions
SubCaption 0 " "
SubCaption 1 " "
SubCaption 2 "Installing Rise Dean Evans EMS Data Service"
SubCaption 3 " "
SubCaption 4 " "

# Installer sections
Section -Main SEC0000
    ReadRegStr $0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" CurrentVersion
    StrCmp $0 "" +1 BeginInstallation
    MessageBox MB_OK "This application requires Java Run-time Environment (JRE) version 6 or above. You can proceed with the installation, but please ensure that correct JRE is installed before running this application."
    BeginInstallation:
    SetOutPath $INSTDIR
    SetOverwrite on
    File Files\RiseDeanEvansEMSService.jar
    File Files\RiseDeanEvansEMSService.ico
    SetOutPath $INSTDIR\web
    File /r Files\web\*
    IfFileExists "$SMSTARTUP\$(^Name).lnk" SkipCreatingShortcut
    SetOutPath $INSTDIR
    CreateShortcut "$SMSTARTUP\$(^Name).lnk" $INSTDIR\RiseDeanEvansEMSService.jar --port=8080 $INSTDIR\RiseDeanEvansEMSService.ico
    SkipCreatingShortcut:
SectionEnd

Section -post SEC0001
    SetOutPath $INSTDIR
    WriteUninstaller $INSTDIR\uninstall.exe
    SetOutPath $SMPROGRAMS\$(^Name)
    CreateShortcut "$SMPROGRAMS\$(^Name)\Uninstall $(^Name).lnk" $INSTDIR\uninstall.exe
    SetOutPath $INSTDIR
    CreateShortcut "$SMPROGRAMS\$(^Name)\$(^Name).lnk" $INSTDIR\RiseDeanEvansEMSService.jar --port=8080 $INSTDIR\RiseDeanEvansEMSService.ico
SectionEnd

# Uninstaller sections
Section -un.Main UNSEC0000
    Delete /REBOOTOK $INSTDIR\RiseDeanEvansEMSService.jar
    Delete /REBOOTOK $INSTDIR\RiseDeanEvansEMSService.ico
    RMDir /r /REBOOTOK $INSTDIR\web\*
    Delete /REBOOTOK "$SMSTARTUP\$(^Name).lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$(^Name)\Uninstall $(^Name).lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$(^Name)\$(^Name).lnk"
    RmDir /REBOOTOK "$SMPROGRAMS\$(^Name)"
SectionEnd

Section -un.post UNSEC0001
    Delete /REBOOTOK $INSTDIR\uninstall.exe
    RmDir /REBOOTOK $INSTDIR
SectionEnd

# Installer functions
Function .onInit
    InitPluginsDir
    !insertmacro MULTIUSER_INIT
FunctionEnd
