class Graduate implements Student {
    public String name;
    public String job;
    public long id;

    Graduate(String name, long id, String job) {
        this.name = name;
        this.id = id;
        this.job = job;
    }

    @Override
    public void showStudentDetails() {
        System.out.println("Graduate");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Job: " + job);
    }
}