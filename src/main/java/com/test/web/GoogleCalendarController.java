package com.test.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar.Events;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;

@Controller
public class GoogleCalendarController {

	private static final String APPLICATION_NAME = "";
	private static HttpTransport httpTransport;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static com.google.api.services.calendar.Calendar client;

	GoogleClientSecrets clientSecrets;
	GoogleAuthorizationCodeFlow flow;
	Credential credential;

	@Value("${google.client.client-id}")
	private String clientId;
	@Value("${google.client.client-secret}")
	private String clientSecret;
	@Value("${google.client.redirectUri}")
	private String redirectURI;

	private Set<Event> events = new HashSet<>();

	final DateTime startTime = new DateTime("2020-09-24T10:00:00.000+05:30");
	final DateTime endTime = new DateTime("2020-09-24T16:30:00.000+05:30");

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	@GetMapping("/get-calendar-events")
	public RedirectView googleConnectionStatus(HttpServletRequest request) throws Exception {
		AuthorizationCodeRequestUrl authorizationUrl;
		if (flow == null) {
			Details web = new Details();
			web.setClientId(clientId);
			web.setClientSecret(clientSecret);
			clientSecrets = new GoogleClientSecrets().setWeb(web);
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
					Collections.singleton(CalendarScopes.CALENDAR)).build();
		}
		authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectURI);
		System.out.println("Calendar authorizationUrl->" + authorizationUrl);
		return new RedirectView(authorizationUrl.build());
	}

	@RequestMapping(value = "/login/google", method = RequestMethod.GET, params = "code")
	public ResponseEntity<String> oauth2Callback(@RequestParam(value = "code") String code) {
		com.google.api.services.calendar.model.Events eventList;
		String message;
		try {
			TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
			credential = flow.createAndStoreCredential(response, "userID");
			client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();
			Events events = client.events();
			eventList = events.list("primary").setTimeMin(startTime).setTimeMax(endTime).execute();
			message = eventList.getItems().toString();
			int count = 1;
			for(Event currEvent: eventList.getItems()) {
				System.out.println("\nMeeting: "+ count);
				System.out.println("Title: "+currEvent.getSummary());
				System.out.println("Title: "+currEvent.getDescription());
				System.out.println("Duration: "+currEvent.getStart().getDateTime()+" - "+currEvent.getEnd().getDateTime());
				count++;
			}
		} catch (Exception e) {
			message = "Exception while handling OAuth2 callback (" + e.getMessage() + ")."
					+ " Redirecting to google connection status page.";
		}
//		System.out.println("Cal Msg:" + message);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

//	public Set<Event> getEvents() throws IOException {
//		return this.events;
//	}

}