# FixMyRide - Vehicle repairs are just a few taps away (Capstone_FixMyRide_CH2-PS481)

## Project Summary
We present a proactive solution—an innovative application meticulously designed to identify early issues faced by riders and provide customized recommendations for effective solutions. Leveraging machine learning recommendation technology, seamlessly integrated with a cloud computing architecture, and tailored for mobile use, the application promises a remarkably user-friendly experience. Moreover, this application encompasses features aimed at expanding users' knowledge base and streamlining the convenient location of nearby repair shops, ushering in a new era of efficiency and convenience for the motorbike repair shop business.

## Our Team Members: 
- (ML) M258BSY1875 – M. Rafly Rahman – Active
- (ML) M296BSX0587 – Wanda Gustrifa – Active
- (ML)  M225BSY0419 – Hendri Kurniawan  – Active
- (MD) A009BSY2567 – Farhan Maulana Muhammad – Active
- (CC) C319BSX4271 – Hajarani Syadzwana – Active
- (CC) C319BSY3334 – Wilbert – Active

## Steps to Replicate the Project 
clone this repository

Machine Learning :
- Gather the required dataset collect the dataset from open and public dataset provider(kaggle, roboflow, etc) or ask an expert or someone who knows the issues. Inthis case, we collect dataset with ask an expert or someone who knows the issues.
- Clean, preprocess, and transform the raw data into a format suitable for training a machine learning model. 
- Do Feature Engineering: Create new features or modify existing ones to improve the model's performance.
- Split the data with a training and validationratio of 75:25
- Build the model start with install all libraries required, we use TensorFlow and Sequential
- Add training again in addition with callbacks=[early_stopping] to avoid the overfitting
- Train the model using google collab notebook
- Plot the loss and accuracy of training and validation into metrics to know the model suit the dataset well. The model suits our dataset well with accuracy >93%
- Save the model in format .h5 using command model.save("your_model_name.h5").
- Make prediction with install all libraries required
- Define the label of our classes (we have 10 classes: masalah mesin, masalah sistem bahan bakar, masalah rem, masalah knalpot, masalah transmisi, tidak ada permasalahan, masalah kopling, masalah pendingin, masalah starter, masalah elektrikal)
- Load the data for testing
- Converted the text into array form.
- Load the model that we saved befored
- Make predictions (we got the prediction for all classes have a confidence value >95%)
- Configure and deploy the model

Cloud Computing :

 Flask  :
  - Build the docker container based on the the dockerfile provided
  - Create a cloud run service
  - Upload the docker container to cloud run, change the mapping port to 8080
  - Test the API in postman

  NodeJS :
  - Create a firestore database, cloud storage bucket, and get API for nearbySearch in GCP
  - Create service account to enable access to firestore and cloud storage
  - Generate the service account credential in a .json file
  - Save the .json file with database.json
  - Generate an API key to access to nearbySearch
  - Open apiKey directory inside the src directory
  - Change the comment line in the apiKey.js 
  - Replace the databases.json file with the service account credential
  - Build the docker container based on the the dockerfile provided
  - Create a cloud run service
  - Upload the docker container to cloud run, change the mapping port to 9000
  - Test the API in postman
  

Mobile Developer :
Android Studio
Kotlin
CameraX to capture user photo
Retrofit to call API
Figma, to design app

## Here Our Link : 
- Here our figma link for app design: 
https://www.figma.com/file/UAtnSgkM0UVz6DzwKXX4lG/Capstone-Project?type=design&node-id=0-1&mode=design
- Here our figma link for cloud design:
  https://www.figma.com/file/ATuLU2Eu79rPIDE6steN2G/FIxmyride-cloud-architecture?type=whiteboard&node-id=0%3A1&t=uS93BTeHjjrE600t-1 
- Here our team timeline (gantt chart): 
https://drive.google.com/file/d/1jR_vNj5oX8yNNSz9QRsQAAJSBShfWX71/view
- Flask API documentation
https://docs.google.com/document/d/1F42jnQnCwdYM1LH4wy-Eqysxha54iEOImfJZTa2E_cg/edit?usp=sharing
- NodeJS API documentation
https://docs.google.com/document/d/1DbqtBKXWrOxFBzU0bSMKnrVhntx-pkjt7pD3rKNNUcI/edit?usp=sharing
- Here our demo video:
  https://youtu.be/vdSRnOGz52Y
- Here our PPTX file:
  https://drive.google.com/file/d/1aJZ2d5YH7Mkab_CTVdzUydeXOtNMwGRl/view?usp=sharing 
