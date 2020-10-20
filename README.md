# Manager Search

a sample app for searching for managers by requesting and filtering a json file

# Architecture and libraries

Based on a MVVM architecture useing Courotines, Flow and LiveData
- Koin used for DI
- Retrofit used as HTTP Client
- moshi-jsonapi for json:api deserialization

# Assumptions
I am assuming the app would leverage a Rest Api that would accept a query parameter. For this solution it adds an extra needed filter when requesting the json file - as it is static in this sample.
An alternative solution could be to load the file once and then do the filtring on that list of items when typing (solution provided in ManagerSearchStaticFileViewModel.kt)

# Installation
clone the repository to your local machine and import it in Android Studio. Hit the green play button in Android Studio - that will build the app and deploy it to your emulator/device.
for running test(s) locate the com.demo.managersearch (androidTest) package - rightclick that and select "Run all tests in com.demo.managersearch "

