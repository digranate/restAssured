package pojos;

/**
 * Created by Dinara.Trifanova on 8/15/2017.
 */
public class User {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public User setAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public User setWebsite(String website) {
        this.website = website;
        return this;
    }

    public Company getCompany() {
        return company;
    }

    public User setCompany(Company company) {
        this.company = company;
        return this;
    }

    public static class Company{
        private String name;
        private String catchPhrase;
        private String bs;

        public String getName() {
            return name;
        }

        public Company setName(String name) {
            this.name = name;
            return this;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public Company setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
            return this;
        }

        public String getBs() {
            return bs;
        }

        public Company setBs(String bs) {
            this.bs = bs;
            return this;
        }
    }

    public static class Address{
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        public String getStreet() {
            return street;
        }

        public Address setStreet(String street) {
            this.street = street;
            return this;
        }

        public String getSuite() {
            return suite;
        }

        public Address setSuite(String suite) {
            this.suite = suite;
            return this;
        }

        public String getCity() {
            return city;
        }

        public Address setCity(String city) {
            this.city = city;
            return this;
        }

        public String getZipcode() {
            return zipcode;
        }

        public Address setZipcode(String zipcode) {
            this.zipcode = zipcode;
            return this;
        }

        public Geo getGeo() {
            return geo;
        }

        public Address setGeo(Geo geo) {
            this.geo = geo;
            return this;
        }

        public static class Geo{
            private String lat;
            private String lng;

            public String getLat() {
                return lat;
            }

            public Geo setLat(String lat) {
                this.lat = lat;
                return this;
            }

            public String getLng() {
                return lng;
            }

            public Geo setLng(String lng) {
                this.lng = lng;
                return this;
            }
        }}

}
