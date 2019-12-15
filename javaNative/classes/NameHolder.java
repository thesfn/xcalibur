package xcalibur.javaNative.classes;

public class NameHolder
{

    public String
            firstname,
            middlename,
            lastname;

    public NameHolder()
    {}

    public NameHolder(String firstName, String middleName, String lastName)
    {
        firstname = firstName;
        middlename = middleName;
        lastname = lastName;
    }

    public String combineAll()
    {
        return firstname + (middlename == null || middlename.isEmpty() ? " "  : " "+middlename+" ")+ lastname;
    }
}
