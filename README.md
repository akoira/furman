#linux
* fonts - see CuttingApp::loadTTF
* adjust
  * data-source.xml
  * repository.xml

#windows
* d:\_nifi\nifi-1.16.0\bin\run-nifi.bat
* sc create "nifi" start=demand displayname= "Nifi" binpath=d:\_nifi\nifi-1.16.0\bin\run-nifi.bat
* Generated Username [bcc0a3a3-a082-4ceb-a96d-6f97b4b0012f]
*  Generated Password [9dbQlVJvYfMwCKYTL2kVIoNqeju5YB7+]


#deployment
* in production: 
  * 2.4
    * https://docs.google.com/spreadsheets/d/1BD_GRKRwo2NKGSXCCaWG5Nmfd1YW16bdYLgJdvSpuac/edit#gid=0
    * 1,2,6
* in progress:
  * 2.4.1
    * liquebase - need separate context
    * mongodb check it works with new driver