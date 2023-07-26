package cis.tinkoff.support.exceptions.constants;

public interface DisplayedErrorMessageKeeper {

    //ProfilePictureNotFoundException
    String PROFILE_PICTURE_NOT_FOUND = "Фото профиля не было найдень.";

    //UnavailableMediaTypeException
    String UNAVAILABLE_MEDIA_TYPE = "Вы загрузили файл недопустимого формата.";

    //UserAlreadyExistsException
    String USER_ALREADY_EXISTS = "Пользователь с таким email уже существует.";

    //RequestAlreadyExistsException
    String REQUEST_ALREADY_EXISTS = "Такая заявка на вступление в проект уже существует и находится на рассмотрении.";

    //InaccessibleActionException
    String INACCESSIBLE_ACTION = "Данное действие недоступно Вам.";

    //RecordNotFoundException
    String RESOURCES_NOT_FOUND = "Такого ресурса не существует или он был удалён.";

    //RequestAlreadyProcessedException
    String REQUEST_ALREADY_PROCESSED = "Данная заявка уже обработана ранее";

}
