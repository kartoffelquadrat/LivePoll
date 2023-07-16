# LivePoll

Fast and anonymous polling.

## About

Every compentent course lecturer has an interest in regularly testing their students understanding.
Unfortunatley students may feel intimidated by personal interaction. This repostiory hosts the
source code and instrutions for a *LivePoll*, a lightweight technical polling application.

### How it works

* The course lecturer runs the platform on their laptop and projects a poll question.
* Students scan a QR code for their prefered answer. No login is required.
* The platform registers votes and shows the stats.
* The lecturer reveals the correct answer and elaborates.

## Usage

### For Lecturers

* Install Java v11 or later
* Install maven
* Checkout the code or donwload a release (TODO: Create release page)
* Start the platform: `mvn clean package spring-boot:run`

#### Poll Packs

### For Students

* Make sure you are in the lcoal university wifi.  
  (This is a local service, it cannot be reached over the internet, only LAN)
* Use your phone or tablet to scan the QR code for your preferred answer.
* Wait for the success message on your phone, do not scan twice.

## Documentation

* Developpers can access the JavaDoc as GitHub page: [Javadoc link](https://m5c.github.io/LivePoll/)
* The API is structured as follows:

```
  WEB (all these are GET, and produce webpage replies)
     - /               => Landing page, chose your activity
     - /quick          => "QuickPoll: Type your question for a fast, single-shot poll"
     - /polls/{pollid} => Rendered page showing question and QR code voting options and stats.
                      (This page is restricted to LOCALHOST)
     - /polls/{pollid}/qr/{option} => Cast vote and retrieve ACK message. In a true REST service 
     this would be a Put/Post option, but for simplicity we use the phones implicitly website GET to cast a vote.
  REST (these are actual API backends, not returning website data but intended for JSON exchange)
     - 
```

> Note: The API is not a true REST API, as we are using GET requests for voting. This is a design
> choice to reduce the voting overhead (no action required other than scanning the code, for
> implicit
> voting).

## Author

Developer: Maximilian Schiedermeier
Email: maximilian.schiedermeier@mcgill.ca