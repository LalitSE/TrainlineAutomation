# Trainline Automation
QA Automation

#To Run on Specific Browser 
clean test site jetty:run -DsuiteXmlFile=XMLSuites/TrainLineDemoTests.xml -Dbrowser=chrome

#To Run on Default Browser 
clean test site jetty:run -DsuiteXmlFile=XMLSuites/TrainLineDemoTests.xml