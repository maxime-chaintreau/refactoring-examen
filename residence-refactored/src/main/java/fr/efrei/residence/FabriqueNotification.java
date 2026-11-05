package fr.efrei.residence;

public class FabriqueNotification {

    private FabriqueNotification() {
    }

    public static CanalNotification creer(Canal canal) {
        return switch (canal) {
            case EMAIL -> new NotificationEmail();
            case SMS -> new NotificationSms(new SmsService());
            case COURRIER -> new NotificationCourrier(new CourrierService());
        };
    }
}