import com.tow.etc.filesystemimage.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.lang.System.out;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Configuration.class)
public class InitTests {
    @Value("${dir.results}")
    private String resultsDirectory;

    @Value("${dir.start}")
    private String imageDirectory;

    @Test
    public void checkProps() {
        assertTrue("You must set prop \"dir.results\"!", !StringUtils.equals(resultsDirectory, "${dir.results}"));
        assertTrue("You must set prop \"dir.start\"!", !StringUtils.equals(imageDirectory, "${dir.start}"));
        out.println(resultsDirectory + " " + imageDirectory);
    }
}
