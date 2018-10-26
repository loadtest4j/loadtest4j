package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class BodyPartTest {
    @Test
    public void testStringPart() {
        final BodyPart bodyPart = BodyPart.string("foo", "abc");

        final String output = bodyPart.match(new BodyPart.Matcher<String>() {
            @Override
            public String stringPart(String name, String content) {
                return name + "," + content;
            }

            @Override
            public String filePart(Path content) {
                return null;
            }
        });

        assertThat(output).isEqualTo("foo,abc");
    }

    @Test
    public void testFilePart() {
        final BodyPart bodyPart = BodyPart.file(Paths.get("/tmp/foo.txt"));

        final String output = bodyPart.match(new BodyPart.Matcher<String>() {
            @Override
            public String stringPart(String name, String content) {
                return null;
            }

            @Override
            public String filePart(Path content) {
                return content.toString();
            }
        });

        assertThat(output).isEqualTo("/tmp/foo.txt");
    }
}
