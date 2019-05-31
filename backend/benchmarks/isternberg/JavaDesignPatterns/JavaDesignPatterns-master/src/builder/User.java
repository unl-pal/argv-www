package builder;

public class User {
    private String id;
    private String name;
    private String title;
    private String description;
    private int score;
    private boolean isActive;

    public User(String id, String name, String title, String description, int score, boolean isActive) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.score = score;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", score=" + score +
                ", isActive=" + isActive +
                '}';
    }

    static class Builder {
        private String id;
        private String name;
        private String title;
        private String description;
        private int age;
        private boolean isActive;


        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setScore(int age) {
            this.age = age;
            return this;
        }

        public Builder setActive(boolean male) {
            this.isActive = male;
            return this;
        }

        public User build() {
            return new User(id, name, title, description, age, isActive);
        }
    }
}

