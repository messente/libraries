/**
 * Copyright 2016 Lennar Kallas, Messente Communications Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.messente.examples.advanced;

import com.messente.enums.HttpMethod;
import com.messente.enums.HttpProtocol;
import com.messente.options.MessenteOptions;
import com.messente.response.MessenteResponse;

/**
 * Example of how to send SMS with pre-configured options.
 *
 * @author Lennar Kallas
 */
public class SendSmsWithOptions {

    public static final String API_USERNAME = "<api-username-here>";
    public static final String API_PASSWORD = "<api-password-here>";

    public static final String SMS_SENDER_ID = "<your-sender-id-here>";
    public static final String SMS_RECIPIENT = "+372512345678";
    public static final String SMS_TEXT = "Hey! Check out messente.com, it's awesome!";

    public static void main(String[] args) {

        // Create Messente client
        Messente messente = new Messente(API_USERNAME, API_PASSWORD);

        // Create SMS options object that you can reuse for each SMS you are sending
        MessenteOptions options = new MessenteOptions.Builder()
                .autoconvert(MessenteOptions.Autoconvert.ON) // Character replacement setting
                .charset("UTF-8") // Encoding for SMS text and sender ID
                .dlrUrl("http://www.yourdomain.com/process_dlr.php") // Delivery report URL
                .httpMethod(HttpMethod.POST) // HTTP method that is used for API call
                .protocol(HttpProtocol.HTTPS) // HTTP protocol used for API call
                .udh(null) // UDH (User Data Header)
                .validity("60") // For how long message is retried if phone is off
                .timeToSend("1453276295") // UNIX timestamp for delayed sending
                .build();   // Finally build options

        // Create response object
        MessenteResponse response = null;

        try {
            // Send SMS
            response = messente.sendSMS(SMS_SENDER_ID, SMS_RECIPIENT, SMS_TEXT, options);

            // Checking the response status
            if (response.isSuccess()) {

                // Get Messente server full response
                System.out.println("Server response: " + response.getResponse());

                //Get unique message ID part of the response(can be used for retrieving message delivery status later)
                System.out.println("SMS unique ID: " + response.getResult());

            } else {
                // In case of failure get failure message                
                throw new RuntimeException(response.getResponseMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed to send SMS! " + e.getMessage());
        }

    }

}
