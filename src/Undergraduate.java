class Undergraduate implements Student {
    private String name;
    private String major;
    private long id;

    Undergraduate(String name, long id, String major) {
        this.name = name;
        this.id = id;
        this.major = major;
    }

    @Override
    public void showStudentDetails() {
        System.out.println("Undergraduate");
        System.out.println("Name : " + name);
        System.out.println("Id : " + id);
        System.out.println("Major : " + major);
    }
}