package bet.notification.service.serviceImp;


import bet.notification.events.listener.NotificationCreatedListener;
import bet.notification.exception.ResourceNotFoundException;
import bet.notification.model.Notification;
import bet.notification.repository.NotificationRepository;
import bet.notification.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class NotificationServiceImp implements NotificationService {

    @Autowired
    private  NotificationRepository notificationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotificationCreatedListener notify;


    @Override
    public void createNotification(Notification messageRequest) {

        notificationRepository.save (messageRequest);

    }

    @Override
    public String CreateNotifications(List<Notification> messages) {
        List<Notification>  notifications = messages.stream ().map (message ->
                modelMapper.map(message,Notification.class)).collect( Collectors.toList());
        notificationRepository.saveAll ( notifications );
        return  "Messages saved Successfully";
    }

    @Override
    public Notification getNotification(Long id) {
        Notification notification = notificationRepository.findById (id).orElseThrow (
                ()-> new ResourceNotFoundException ("Notification with ID " + id + " not found.") );
        return modelMapper.map (notification, Notification.class);
    }

    @Override
    public List<Notification> getAllNotification() {
        return notificationRepository.findAll ().stream ().map ( notification -> modelMapper
                .map (notification, Notification.class)).collect( Collectors.toList());
    }

    @Override
    public String upDateNotification(Notification messageRequest, Long id) {
        Notification notifRepo = notificationRepository.findById (id).orElseThrow (
                ()-> new ResourceNotFoundException ("Notification with ID " + id + " not found."));
         Notification notification = modelMapper.map ( messageRequest, Notification.class);
        notifRepo.update ( notification );
        notificationRepository.save (notification);
        return "Notification Updated successfully";
    }

    @Override
    public String deleteNotification(Long id) {
        notificationRepository.deleteById ( id);
        return "Notification Deleted successfully";
    }

    @Override
    public String deleteAll() {
        notificationRepository.deleteAll ();
        return "All Notification are cleared";
    }

     @Override
     public Notification sendNotification() {


         return null;
     }

}
