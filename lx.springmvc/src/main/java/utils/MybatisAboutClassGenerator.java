package utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import springmvc.vo.ColumnProperty;
import springmvc.vo.Lianjia;
import springmvc.vo.Table;

public class MybatisAboutClassGenerator {

    private static String MAPPER_SUFFIX = "Mapper.java";
    private static String DAO_SUFFIX = "Dao.java";
    private static String SPACE_TAB = "    ";

    private static String SELECT = SPACE_TAB + "@Select(\"select * from %s where %s = #{%s}\")\n";
    private static String RESULT = SPACE_TAB + "        @Result(column = \"%s\", property = \"%s\")";
    private static String SELECT_METHOD = SPACE_TAB + "public %s by%s(int %s);\n\n";

    private static String INSERT = SPACE_TAB + "@Insert(\"insert into %s (%s) values (%s)\")\n";
    private static String INSERT_METHOD = SPACE_TAB + "public boolean insert(%s %s);\n\n";

    private static String UPDATE = SPACE_TAB + "@Update(\"update %s set %s where %s\")\n";
    private static String UPDATE_METHOD = SPACE_TAB + "public boolean update%s(%s %s); \n\n";

    private static String DELETE = SPACE_TAB + "@Delete(\"delete from %s where %s\")\n";
    private static String DELETE_METHOD = SPACE_TAB + "public int delete(%s %s);\n";

    public static void main(String[] args) throws IOException {
        Class<Lianjia> _class = Lianjia.class;
        Table tableName = _class.getAnnotation(Table.class);
        if (tableName == null || StringUtils.isEmpty(tableName.value())) {
            return;
        }
        String canonicalName = _class.getCanonicalName();
        if (!canonicalName.contains(".vo.")) {
            return;
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
        File file = createClassHead(_class, mapperPath);
        Field[] fields = _class.getDeclaredFields();
        // Annotation List
        selectPart(_class, tableName, file, fields);

        insertPart(_class, tableName, file, fields);

        updatePart(_class, tableName, file, fields);

        deletePart(_class, tableName, file, fields);

        FileUtils.write(file, "}", true);
    }

    private static void deletePart(Class<Lianjia> _class, Table tableName, File file, Field[] fields)
            throws IOException {
        ColumnProperty pk = null;
        Field fieldPk = null;
        for (Field field : fields) {
            if (field.getModifiers() == Modifier.STATIC) {
                continue;
            }
            ColumnProperty column = field.getAnnotation(ColumnProperty.class);
            if (column == null || StringUtils.isEmpty(column.value())) {
                continue;
            }
            if (column.isPk()) {
                pk = column;
                fieldPk = field;
            }
        }

        String wherePart = String.format("%s=#{%s}", pk.value(), fieldPk.getName());
        FileUtils.write(file, String.format(DELETE, tableName.value(), wherePart), true);
        FileUtils.write(file,
                String.format(DELETE_METHOD, _class.getSimpleName(), StringUtils.uncapitalize(_class.getSimpleName())),
                true);
    }

    private static void updatePart(Class<Lianjia> _class, Table tableName, File file, Field[] fields)
            throws IOException {
        Map<ColumnProperty, Field> needUpdateKeys = new HashMap<ColumnProperty, Field>();
        ColumnProperty pk = null;
        Field fieldPk = null;
        for (Field field : fields) {
            if (field.getModifiers() == Modifier.STATIC) {
                continue;
            }
            ColumnProperty column = field.getAnnotation(ColumnProperty.class);
            if (column == null || StringUtils.isEmpty(column.value())) {
                continue;
            }
            if (column.isPk()) {
                pk = column;
                fieldPk = field;
            }
            if (!column.needUpdate()) {
                continue;
            }
            needUpdateKeys.put(column, field);
        }
        for (Entry<ColumnProperty, Field> entry : needUpdateKeys.entrySet()) {
            String updatePart = String.format("%s=#{%s} ", entry.getKey().value(), entry.getValue().getName());
            String wherePart = String.format("%s=#{%s} ", pk.value(), fieldPk.getName());
            FileUtils.write(file, String.format(UPDATE, tableName.value(), updatePart, wherePart), true);
            FileUtils.write(file, String.format(UPDATE_METHOD, StringUtils.capitalize(entry.getValue().getName()),
                    _class.getSimpleName(), _class.getSimpleName()), true);
        }

        String updatePart = "";
        String wherePart = String.format("%s=#{%s} ", pk.value(), fieldPk.getName());
        for (Entry<ColumnProperty, Field> entry : needUpdateKeys.entrySet()) {
            if (StringUtils.isEmpty(updatePart)) {
                updatePart = String.format("%s=#{%s} ", entry.getKey().value(), entry.getValue().getName());
            } else {
                updatePart += String.format(",%s=#{%s} ", entry.getKey().value(), entry.getValue().getName());
            }
        }
        FileUtils.write(file, String.format(UPDATE, tableName.value(), updatePart, wherePart), true);
        FileUtils.write(file, String.format(UPDATE_METHOD, "", _class.getSimpleName(), _class.getSimpleName()), true);
    }

    private static void insertPart(Class<Lianjia> _class, Table tableName, File file, Field[] fields)
            throws IOException {
        String columnsPart = "";
        String valuesPart = "";
        for (Field field : fields) {
            if (field.getModifiers() == Modifier.STATIC) {
                continue;
            }
            ColumnProperty column = field.getAnnotation(ColumnProperty.class);
            if (column == null || StringUtils.isEmpty(column.value()) || column.isIncrement()) {
                continue;
            }
            if (StringUtils.isEmpty(columnsPart)) {
                columnsPart = column.value();
                valuesPart = "#{" + field.getName() + "}";
            } else {
                columnsPart += ", " + column.value();
                valuesPart += ", #{" + field.getName() + "}";
            }
        }
        FileUtils.write(file, String.format(INSERT, tableName.value(), columnsPart, valuesPart), true);
        FileUtils.write(file, String.format(INSERT_METHOD, _class.getSimpleName(), _class.getSimpleName()), true);
    }

    private static void selectPart(Class<Lianjia> _class, Table tableName, File file, Field[] fields)
            throws IOException {
        Map<ColumnProperty, Field> isSelectKeys = new HashMap<ColumnProperty, Field>();
        for (Field field : fields) {
            if (field.getModifiers() == Modifier.STATIC) {
                continue;
            }
            ColumnProperty column = field.getAnnotation(ColumnProperty.class);
            if (column == null || StringUtils.isEmpty(column.value()) || !(column.isSelectKey() || column.isPk())) {
                continue;
            }
            isSelectKeys.put(column, field);
        }
        for (Entry<ColumnProperty, Field> entry : isSelectKeys.entrySet()) {

            FileUtils.write(file,
                    String.format(SELECT, tableName.value(), entry.getKey().value(), entry.getValue().getName()), true);
            FileUtils.write(file, SPACE_TAB + "@Results(value = { //\n", true);

            Map<ColumnProperty, Field> usefulList = new HashMap<ColumnProperty, Field>();
            for (Field field : fields) {
                if (field.getModifiers() == Modifier.STATIC) {
                    continue;
                }
                ColumnProperty fieldName = field.getAnnotation(ColumnProperty.class);
                if (fieldName == null || StringUtils.isEmpty(fieldName.value())) {
                    continue;
                }
                usefulList.put(fieldName, field);
            }
            int pos = 0;
            for (Entry<ColumnProperty, Field> _entry : usefulList.entrySet()) {
                FileUtils.write(file, String.format(RESULT, _entry.getKey().value(), _entry.getValue().getName()),
                        true);
                pos++;
                if (pos == usefulList.size()) {
                    FileUtils.write(file, " })\n", true);
                } else {
                    FileUtils.write(file, ", //\n", true);
                }
            }
            FileUtils.write(file, String.format(SELECT_METHOD, _class.getSimpleName(), entry.getValue().getName(),
                    entry.getValue().getName()), true);
        }
    }

    private static File createClassHead(Class<Lianjia> _class, String mapperPath) throws IOException {
        String mapperName = _class.getSimpleName() + "Mapper";
        String fullMapperName = mapperPath + "/" + _class.getSimpleName() + MAPPER_SUFFIX;
        File file = new File(fullMapperName);
        String packageName = _class.getPackage().getName().replace(".vo", ".dao.mapper");
        FileUtils.write(file, "package " + packageName + ";");
        FileUtils.write(file, "\n\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Insert;\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Result;\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Results;\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Select;\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Update;\n", true);
        FileUtils.write(file, "import org.apache.ibatis.annotations.Delete;\n", true);
        FileUtils.write(file, "import " + _class.getName() + ";\n\n", true);

        FileUtils.write(file, "public interface " + mapperName + " {\n\n", true);
        return file;
    }

}
