package by.dak.furman.templateimport.parser.category;

import by.dak.furman.templateimport.parser.AParser;
import by.dak.furman.templateimport.parser.template.TemplateParser;
import by.dak.furman.templateimport.values.ACategory;
import by.dak.furman.templateimport.values.Category;
import by.dak.furman.templateimport.values.Message;
import by.dak.furman.templateimport.values.TemplateCategory;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * User: akoyro
 * Date: 5/1/13
 * Time: 5:33 PM
 */
public class CategoryParser extends AParser
{
    private static final Logger LOGGER = LogManager.getLogger(CategoryParser.class);
    private static final String PATTERN_DIR_DOCS = "^Деталировка[\\W\\w\\d\\.\\ \\)\\(-]*";
    private static final String PATTERN_FILE_DOC = "^[^~][\\W\\d\\w\\.\\ ]*\\.doc$";
    private File baseDir;
    private List<Category> categories = new ArrayList<Category>();
    private Delegate delegate;

    public static void main(String[] args)
    {
        CategoryParser parser = new CategoryParser();
        parser.setBaseDir(new File("/Users/akoyro/_projects/modernhouse/_test.data/БАЗА"));
        parser.parse();
    }

    public void parse()
    {
        if (!getBaseDir().exists())
            throw new IllegalArgumentException(String.format("File %s does not exist", getBaseDir().getAbsolutePath()));
        List<File> dirs = FileFilterUtils.filterList(DirectoryFileFilter.DIRECTORY, getBaseDir().listFiles());
        for (File dir : dirs)
        {
            Category category = addCategory(null, Category.valueOf(dir.getName(), dir));
            parseCategoryDir(dir, category);
            categories.add(category);
            if (delegate != null)
                delegate.categoryAdded(category);
        }
    }

    private <C extends ACategory> C addCategory(Category parent, C child)
    {
        if (parent != null)
        {
            parent.addChild(child);
            child.setParent(parent);
        }
        return child;
    }

    private void parseCategoryDir(File parentDir, Category parent)
    {
        LOGGER.info(String.format("Parser category dir %s", parentDir.getAbsolutePath()));
        List<File> dirs = FileFilterUtils.filterList(DirectoryFileFilter.DIRECTORY, parentDir.listFiles());
        for (File dir : dirs)
        {
            TemplateCategory category = addCategory(parent, TemplateCategory.valueOf(dir));

            LOGGER.info(String.format("Parser category dir %s", dir.getAbsolutePath()));
            RegexFileFilter filter = new RegexFileFilter(PATTERN_DIR_DOCS, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS | Pattern.UNICODE_CASE);
            List<File> files = FileFilterUtils.filterList(filter, dir.listFiles());
            if (files.size() > 0)
                parseTemplates(files.get(0), category);
            else
            {
                Message message = Message.valueOf(Error.dirEmpty.getKey(), dir.getAbsolutePath());
                category.addMessage(Error.dirEmpty.getKey());
                if (delegate != null)
                    delegate.messageAdded(category, message);
            }

        }
    }

    private void parseTemplates(File docsDir, TemplateCategory parent)
    {
        LOGGER.info(String.format("Parser templates dir %s", docsDir.getAbsolutePath()));
        RegexFileFilter filter = new RegexFileFilter(PATTERN_FILE_DOC, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS);
        List<File> files = FileFilterUtils.filterList(filter, docsDir.listFiles());
        if (files.size() == 0)
        {
            parent.addMessage(Error.noDocFiles.getKey());
            return;
        }

        for (File docFile : files)
        {
            TemplateParser parser = new TemplateParser();
            parser.setDocFile(docFile);
            parser.parse();

            parent.addChild(parser.getTemplate());
        }

    }

    public List<Category> getCategories()
    {
        return categories;
    }

    public File getBaseDir()
    {
        return baseDir;
    }

    public void setBaseDir(File baseDir)
    {
        this.baseDir = baseDir;
    }

    public Delegate getDelegate()
    {
        return delegate;
    }

    public void setDelegate(Delegate delegate)
    {
        this.delegate = delegate;
    }

    public static interface Delegate
    {
        public void categoryAdded(ACategory added);

        public void messageAdded(ACategory category, Message message);
    }
}
