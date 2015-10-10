# webhook-listener
This is a sample webhook listener that takes data and persists it into a mongo database.

## Setup
  1. install mongoDB
  2. install Java 8
  3. install gradle
  4. install Pivotal Cloud Foundary command line client http://docs.pivotal.io/pivotalcf/devguide/installcf/install-go-cli.html

## How To Run

### 1. Build
  `./gradlew clean build`
  note: `./gradlew clean assemble` will create deployable artifact without running unit/integration tests
### 2. Run Executable
  `java -jar build/libs/a6-subscriber-1.0.jar`
  note: the application will by default start on port 9090
## How To Deploy
  1. Think of a creative name for your application
  2. Remember that creative name
  3. Login to Cloud Foundry: `cf login -a https://api.run.pivotal.io`
  4. Create the mongoDB service: `cf create-service mongolab sandbox mongoDB`
  5. Recall that creative name
  6. Create the App with that creative name: `cf push CREATIVE_UNIQUE_APP_NAME -p build/libs/a6-subscriber-1.0.jar --no-start`
  7. Bind your service to your app: `cf bind-service CREATIVE_UNIQUE_APP_NAME mongoDB`
  8. Start your application: `cf start CREATIVE_UNIQUE_APP_NAME`
