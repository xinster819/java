package utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Select;

import springmvc.vo.FieldName;
import springmvc.vo.Lianjia;
import springmvc.vo.TableName;

public class MybatisAboutClassGenerator {

    private static String MAPPER_SUFFIX = "Mapper.java";
    private static String DAO_SUFFIX = "Dao.java";
    
    private static String SELECT = "@Select(\"select * from %s where id = #{id}\")";
    
    public static void main(String[] args) throws IOException {
        Class<Lianjia> _class = Lianjia.class;
        if (!_class.isAnnotation()) {
            return ;
        }
        TableName tableName = _class.getAnnotation(TableName.class);
        if (tableName == null || StringUtils.isEmpty(tableName.value())) {
            return;
        }
        String canonicalName = _class.getCanonicalName();
        if (!canonicalName.contains(".vo.")) {
            return;
        }
        Field[] fields = _class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getModifiers() == Modifier.STATIC) {
                continue;
            }
            FieldName fieldName = field.getAnnotation(FieldName.class);
            if (fieldName == null || StringUtils.isEmpty(fieldName.value())) {
                continue;
            }
            System.out.println(fieldName.value());
        }
        String folderPath = "/";
        String daoPath = folderPath + "dao";
        String mapperPath = daoPath + "/mapper";
        File daoFolder = new File(daoPath);
        if (!daoFolder.isDirectory()) {
            daoFolder.mkdir();
        }
        File mapperFolder = new File(mapperPath);
        if (!mapperFolder.isDirectory()) {
            mapperFolder.mkdir();
        }
        String mapperName = _class.getSimpleName() + "Mapper";
        String fullMapperName = mapperPath + "/" + _class.getSimpleName() + MAPPER_SUFFIX;
        File file = new File(fullMapperName);
        String packageName = _class.getPackage().getName().replace(".vo", ".dao.mapper");
        FileUtils.write(file, "package " + packageName);
        FileUtils.write(file, "\n\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Insert;\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Result;\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Results;\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Select;\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Update;\n", true);
        FileUtils.write(file, "import " + _class.getName()+ ";\n\n", true);
        
        FileUtils.write(file, "public interface " + mapperName + " {\n\n", true);
        
        FileUtils.write(file, String.format(SELECT, tableName.value()), true);
        
        
    }

}
