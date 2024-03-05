# Count Paths
Use a SAX parser to get events from an XML file and count them.  Can also do so in more detail by parsing out person IDs, dates and concepts.

## Run
- from eclipse...
- from command line with Maven: mvn clean compile exec:java
- Each run generates an output file for each input file listed below, with the same name and ".out" appended.
  

## Sample Documents
Some sample CCDA documents are copied in here as recommended by eHealth Exchange.
- [CCD - Ambulatory](https://nam02.safelinks.protection.outlook.com/?url=https%3A%2F%2Fgithub.com%2Fchb%2Fsample_ccdas%2Fblob%2Fmaster%2FNIST%2520Samples%2FCCDA_CCD_b1_Ambulatory_v2.xml&data=05%7C02%7CCHRIS.ROEDER%40cuanschutz.edu%7C90158acfe2c5428f224208dc3c8b4488%7C563337caa517421aaae01aa5b414fd7f%7C0%7C0%7C638451816059410910%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C0%7C%7C%7C&sdata=q6nEWK%2BsCfBxW0q5EVN%2BEQEksWmo32mfro%2BQjjYbLHE%3D&reserved=0)
- [CCD Ambulatory Transition of Care](https://nam02.safelinks.protection.outlook.com/?url=https%3A%2F%2Fgithub.com%2Fgecole%2FHL7-Task-Force-Examples%2Fblob%2Fmaster%2F170.314b2_AmbulatoryToC.xml&data=05%7C02%7CCHRIS.ROEDER%40cuanschutz.edu%7C90158acfe2c5428f224208dc3c8b4488%7C563337caa517421aaae01aa5b414fd7f%7C0%7C0%7C638451816059421436%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C0%7C%7C%7C&sdata=T9dSJEKtiTyDsgHrxZToKAeyprt8XVz54r7AaNh%2F0sQ%3D&reserved=0)
- [CCD Inpatient](https://nam02.safelinks.protection.outlook.com/?url=https%3A%2F%2Fgithub.com%2Fchb%2Fsample_ccdas%2Fblob%2Fmaster%2FNIST%2520Samples%2FCCDA_CCD_b1_InPatient_v2.xml&data=05%7C02%7CCHRIS.ROEDER%40cuanschutz.edu%7C90158acfe2c5428f224208dc3c8b4488%7C563337caa517421aaae01aa5b414fd7f%7C0%7C0%7C638451816059428782%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C0%7C%7C%7C&sdata=swbBQAtcDweN%2BvOc9oTjFrFi%2B%2FQdVQgmknFDD9owucg%3D&reserved=0)
- [CCD Inpatient Transition of Care](https://nam02.safelinks.protection.outlook.com/?url=https%3A%2F%2Fgithub.com%2Fchb%2Fsample_ccdas%2Fblob%2Fmaster%2FTransitions%2520of%2520Care%2520Samples%2FToC_CCDA_CCD_CompGuideSample_FullXML.xml&data=05%7C02%7CCHRIS.ROEDER%40cuanschutz.edu%7C90158acfe2c5428f224208dc3c8b4488%7C563337caa517421aaae01aa5b414fd7f%7C0%7C0%7C638451816059435590%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C0%7C%7C%7C&sdata=cDJw9ptcD%2FR%2B02Hgl0Gl17oe0%2FLNkAtkrBrXM%2FmDrJQ%3D&reserved=0)
