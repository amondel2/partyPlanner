Party Planner
============

A tool for managing guest and seating arrangments for multiple parties. 
I made so major changes in the 2.0 release to support multiple parties..the first version 2.0.  I will be using it so if I find any bugs I will try and fix them.



This Tool is easy to use or at least thats the point.

1. Gettiong Guest Into the system

    a. Ussing Excel
    
        1. I have included am example spreedsheet that can slurpped in. 
        
        2. Once a guest is slurpped in each time the sheet is slurrped it will update that guest.  
    
        3. Guest are unquie by First, Middle, and Last names.  If you want to change a name please do it in the actual application.  
        
    b. Manully Entering Data
    
        1. Go in and add a user
        
        2. You Can Modify it via scaffolding or in the actual ui.
        
        3. This is the only way to manually delete a user.

    
2. TO complie and run just run

    a.  This tool is currently running against grail 2.3.5 and jquery 2.0.3 so no support for crappy old ie.
    
    b. Configure your Datasource or external groovy config file with the ENV var or partyconfig.  I

    c. run grails run-app or grails war (see http://grails.org/doc/2.3.5/ref/Command%20Line/war.html)
   

