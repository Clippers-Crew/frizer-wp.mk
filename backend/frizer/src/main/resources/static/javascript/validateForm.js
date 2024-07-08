function validateForm() {
    const ratingInput = document.querySelector(".reviewAddForm #rating")

    if (ratingInput) {
        const ratingValue = parseFloat(ratingInput.value);
        if (!isNaN(ratingValue) && ratingValue >= 0 && ratingValue <= 5) {
            return true;
        }
    }
    alert("Оценката мора да биде помеѓу 0 и 5")
    return false;
}

function validateRegisterForm() {
    const password = document.querySelector("#registerForm #password").value;
    const repeatedPassword = document.querySelector("#registerForm #repeatedPassword").value;
    const phoneNumberInput = document.querySelector("#registerForm #phoneNumber").value;

    const isPhoneOk = (phoneNumberInput.length === 9 && phoneNumberInput.startsWith("07")) ||
        (phoneNumberInput.length === 12 && phoneNumberInput.startsWith("+3897"));

    if (!isPhoneOk) {
        alert("Телефонскиот број мора да е 9 карактери и да почнува со 07 или +3897.");
        return false;
    }

    if (!isPasswordOk(password)) {
        alert("Лозинката мора да има барем 8 карактери, една голема буква, една мала буква, еден број и еден специјален карактер.");
        return false;
    }

    if (password !== repeatedPassword) {
        alert("Лозинките не се совпаѓаат.");
        return false;
    }

    return true;
}

function isPasswordOk(password) {
    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumber = /[0-9]/.test(password);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

    return password.length >= minLength && hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar;
}
