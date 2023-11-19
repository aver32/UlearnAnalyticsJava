package vkApi;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.UserXtrRole;
import com.vk.api.sdk.objects.users.Fields;
import configUtils.ConfigFileReader;
import models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class VkRepository {
    private final long APP_ID;
    private final String CODE;
    private final VkApiClient vk;
    private final UserActor actor;
    private final String CLUB_PROG_ID = "basicprogrammingrtf2022";

    public VkRepository() throws ClientException, ApiException {
        TransportClient transportClient = new HttpTransportClient();
        List<String> properties = ConfigFileReader.getProperties();
        APP_ID = Long.parseLong(properties.get(1));
        CODE = properties.get(0);

        vk = new VkApiClient(transportClient);
        actor =  new UserActor(APP_ID, CODE);
    }

    public List<UserXtrRole> getUlearnGroupMembers() throws ClientException, ApiException, InterruptedException {
        var response = vk.groups()
                .getMembersWithFields(actor, Fields.CITY)
                .groupId(CLUB_PROG_ID)
                .execute()
                .getItems();

        return response;
    }

    public void addCitiesToStudents(ArrayList<Student> students){
        try{
            var groups = getUlearnGroupMembers();
            for (var student : students){
                var optionalCity = groups.stream()
                        .filter(x -> x.getFirstName().equals(student.getName())
                                && x.getLastName().equals(student.getSurname()))
                        .findFirst();
                if (optionalCity.isEmpty()){
                    continue;
                }
                var city = optionalCity.get().getCity();
                if (city == null){
                    continue;
                }
                var cityString = city.getTitle();
                student.setCity(cityString);
            }
        }
        catch (ClientException | ApiException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
