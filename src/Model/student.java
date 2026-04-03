// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------
package Model;

public class student {

    int id;
    String f_name, l_name, email, phone, date, gender, course, address;

    public student() {
    }

    public student(int id, String f_name, String l_name, String email, String phone, String date, String gender, String course, String address) {
        this.id = id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
        this.phone = phone;
        this.date = date;
        this.gender = gender;
        this.course = course;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "----------------------------- Student Information -----------------------------\n" + "---> ID => " + id + "\n---> Student Name => " + f_name + " " + l_name + "\n---> Email => " + email + "\n---> Phone Number => " + phone + "\n---> Date Of Birth => " + date + "\n---> Gender => " + gender + "\n---> Course => " + course + "\n---> Address => " + address;
    }
}

// -----------------------------------------------------------------------------
// ---> Student Name: Ahmed Mohammed Al-Farani
// ---> Student ID: 1320236338
// ---> Engeneer Name: Mahmoud Ashour
// ---> Final Project: Code For Gaza Company
// -----------------------------------------------------------------------------
