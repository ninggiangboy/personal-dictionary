package prj.dictionary.constant;

public interface IConstant {
    String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*#?&_]{8,}$";
    String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    String ADMIN_ROLE_NAME = "ADMIN";
    String USER_ROLE_NAME = "USER";
    String UPLOAD_IMAGE_PATH = "uploads";
    String[] IMAGE_FILE_EXTENSIONS = {"png", "jpg", "jpeg", "bmp"};
    Double MAX_FILE_SIZE_MEGABYTES = 5_000_000.0;
    int WORDS_PER_PAGE = 5;

    String SPECIAL_CHAR_REGEX = "[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?|\\\\\\/~`\\-]+";
}
