zamboorabank
============

PayU Bank App for the kingdom of Zamboora

Introduction
============

*[Zamboora Bank](https://github.com/abhimanyugupta07/zamboorabank) is a simple banking application for handling credit/debit transactions of the kingdom of Zamboora *

This service is built using Spring MVC Framework.

Endpoints
=========

1. GET /accounts/info/{resident_id}?number=x

It takes one path variable and one request parameter.  

Returns: JSON response containing the information about the user account along with the transactions assosciated with this account. The number of transactions returned can be controlled by the request parameter number.

	 Sample API call: http://your-host-address/accounts/info/abhi?number=10
	 Sample response: {
    "summary": {
        "amount": 2100,
        "residentId": "abhi",
        "bonusBitcoins": 2100,
        "amountBlocked": 0
    },
    "transactions": [
        {
            "timestamp": "Jan 3, 2014 7:57:30 PM",
            "amount": 1000,
            "residentId": "abhi",
            "transactionId": "22cb6098-2293-4c5d-ac4e-459280690e64",
            "uniqueId": 1
        }
    ]
  } 

2. POST /account/{resident_id}/credit?&amount=x

It takes two request parameters resident_id and amount to be credited.

Returns: JSON response containing either the success or error message along with the updated account balance.

    Sample API Call: localhost:8080/account/abhi/credit?amount=1000
    Sample Response: {
      "Current Account Balance": 1000,
      "Success": "Credited account with 1000.0"
    }

3. POST /account/{resident_id}/debit?&amount=x

It takes two request parameters resident_id and amount to be credited. This API call also updates the cashback that the user gets as a reward. This cashback is calculated based on certain percentage which can be set in the payubank.properties file.

Returns: JSON response containing either the success or error message along with the updated account balance.

    Sample API Call: localhost:8080/account/abhi/debit?amount=1000
    Sample Response: {
      "Current Account Balance": 1200,
      "Success": "Debited account with 1000.0"
    }
  
Errors returned by the service
==============================

1. Thrown when a user tried to debit amount more than the current account balance.
	
		{"Current Account Balance":300.0,"Error":"Not Enough Balance in Account"}

2. Thrown when a user tries to debit money from the account which has not yet been created. An account is created when       money is first time credited in the account.

		{"Error":"Account does not exist, please credit the account with some bitcoins before debit"}

3. Thrown when the number provided in the accounts-info API is not a valid number. eg. number=12fhdjfhsk

		{"Error":"Invalid Number format for the parameter number"}
