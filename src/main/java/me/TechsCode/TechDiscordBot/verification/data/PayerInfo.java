package me.TechsCode.TechDiscordBot.verification.data;

import com.google.gson.JsonObject;

public class PayerInfo extends JsonSerializable {
    private String id;
    private String email;
    private String name;
    private String countryCode;

    public PayerInfo(String id, String email, String name, String countryCode){
        this.id = id;
        this.email = email;
        this.name = name;
        this.countryCode = countryCode;
    }

    public PayerInfo(JsonObject state){
        setState(state);
    }

    @Override
    public void setState(JsonObject jsonObject) {
        this.id = jsonObject.get("id").getAsString();
        this.email = jsonObject.get("email").getAsString();
        this.name = jsonObject.get("name").getAsString();
        this.countryCode = jsonObject.get("countryCode").getAsString();
    }

    @Override
    public JsonObject getState() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("countryCode", countryCode);
        return jsonObject;
    }

    public JsonObject getStateCensored() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);

        String[] emailParts = email.split("@");
        int emailPart1Lenght = emailParts[0].length() - 3;
        String emailPart1 = email.substring(0, 4);
        for (int i = 0; i < emailPart1Lenght; i++) {
            emailPart1 += "*";
        }
        String email_censored = emailPart1 + "@" + emailParts[1];

        jsonObject.addProperty("email", email_censored);

        String[] nameParts = name.split(" ");

        String firstName = nameParts[0];
        String fullName = firstName;
        if(nameParts.length > 1){
            String firstName_firstLetter = firstName.substring(0, 1);

            String lastName = "";
            lastName = ". " + nameParts[1];

            fullName = firstName_firstLetter + lastName;
        }

        jsonObject.addProperty("name", fullName);
        jsonObject.addProperty("countryCode", countryCode);
        return jsonObject;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
