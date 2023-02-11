package core;


import org.apache.tools.ant.Project;
import org.gradle.api.Plugin;

public class TestPlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        target.createTask("javaTask");
    }

}