# second-encore-project
springboot-mariadb


[2021.03.19 금요일] : 20210318_AOP_JPA 대망의 스프링 부트 
1. 나의 정보 -> 사진 수정 메뉴 -> 사진 업데이트 
    1.1. filedownload 기능 
2. Spring Boot 
    1.0 특징 : gradle 사용 
               내장 톰캣 사용, 클라우드 배포
    
    1.1 프로젝트 생성 - File -> New -> Spring Starter Project
    1.2 HelloController 생성 
    1.3 src/main/resource/application.properties 포트 변경 server.port=8181  -> Spring boot App -> webpage에서 URL 입력 http://localhost:8181/

3.   ■ src/main/java 
   - 자바소스 폴더
 
    ■ SapmleApplication 
    - 애플리케이션을 시작할 수 있는 main메소드가 존재하는 스프링 구성
        메인 클래스
    
    ■ src/main/resources/static
    - HTML, 스타일 시트, 자바스크립트, 이미지 등의 정적 리소스 폴더

    ■ application.properties  
    - 애플리케이션 및 스프링의 설정 등에서 사용할 여러 가지 프로퍼티(property)정의

    ■ Project and External Dependencies :
    - Gradle에 명시된 프로젝트의 필수 라이브러리 모음   

    ■ src 
    - JSP등 리소스 디렉토리

    ■ build.gradle   
    - Gradle 빌드 명세, 프로젝트에 필요한 라이브러리 관리, 빌드 배포 설정
    - 스프링 부트의 버전을 명시
    - 자바 버전 명시
    - 의존성 옵션
        implementation: 의존 라이브러리 수정시 본 모듈까지만 재빌드(재컴파일)
        api: 의존 라이브러리 수정시 본 모듈을 의존하는 모듈들도 재빌드(재컴파일)
        compileOnly: compile 시에만 빌드하고 빌드 결과물에는 포함하지 않음
        runtime(실행)시 필요없는 라이브러리인 경우 
        runtimeOnly: runtime 시에만 필요한 라이브러리인 경우
        providedRuntime: 실행시 제공되는 library
        testImplementation: 테스트시 관련 library 제공

    3.1 BOOT DASHBOARD 
4.JSP 설정 
    4.1 devtools 설정 : 서버 재구동이 자동으로 됨
                     // https://mvnrepository.com/  
                     // spring-boot-devtools 검색  
                     // 2.4.3
                      - build.gradle -> Dependencies에 추가 
    4.2  /src/main/resources/application.properties 변경 - # DEVTOOLS (DevToolsProperties) spring.devtools.livereload.enabled=true 추가
    4.3  https://mvnrepository.com/  
         - JSTL, tomcat-embed-jasper 검색
        
        한글 입력하고 저장 안될 때는 properties 들어가서 utf-8로 바꿈 
    4.4  src/main 폴더에 webapp/WEB-INF/views 폴더 생성
            controller 에 추가하고 http://localhost:8181/test 해야져 
    4.5 static 안에 사진 등 넣는다 
5.마리아 db
    - mariadb-10.3.28-winx64.zip 선택 
    - C:\JCS\99.MariaDB\mariadb-10.3\data\my.ini 한글 설정 파일 

            [mysqld]
            datadir=C:/JCS/99.MariaDB/mariadb-10.3/data
            port=3306
            init_connect="SET collation_connection = utf8_general_ci"  
            init_connect="SET NAMES utf8"  
            character-set-server = utf8
            collation-server = utf8_general_ci
            [client]
            port=3306
            plugin-dir=C:/JCS/99.MariaDB/mariadb-10.3/lib/plugin
            default-character-set = utf8
            [mysqldump]
            default-character-set = utf8
            [mysql]
            default-character-set = utf8
    
    - 실행용 배치 파일 생성 : C:\JCS\8.Spring\mariadb_server.bat 

        C: 
        CD/ 
        CD study/datadown/mariadb-10.3/bin 
        mysqld.exe
        
        넣고 저장 
    
    -mysql80 이라는 서비스 실행중이라면 중지시킨다. 3306
    -bat 파일 실행해서 배치파일 관리자로 실행 

    - 루트접속 배치 파일 생성 : C:\JCS\8.Springmariadb_server.bat
      
            C: 
            CD/ 
            C:\JCS\99.MariaDB\mariadb-10.3\bin
            start mysql.exe -u root

    - 안되면 mariadb_server.bat 로 실행한 서비스 MYSQLD.EXE 중지하고 다시실행 
    - Query 
        : show databases;
        : use mysql;
        : select host, user, password from user;
        : update user set password=password('root') where user='root';
        : select host, user, password from user;
        : flush privileges;
        : exit
    - mariadb_root.bat 에서 start mysql.exe -u root -> start mysql.exe -u root -root 로 변경 
    - Query 
        : create database jcs;
        : show databases;

6. Form 값의 검증 validator interface 사용 - controller 에서 form 데이터 검증 

[2021.03.22 월요일]
1. 유효성 검사 두가지 방법 - 자바스크립트, 컨트롤러 
   1.1 어노테이션 검증
    - Gradle dependency 추가 
        : implementation 'org.springframework.boot:spring-boot-starter-validation' 
    - VO
        // 우선순위: @NotEmpty -> @Size
        @NotEmpty(message = "메뉴명은 필수 입력입니다(Not empty).")
        @Size(min = 2, max = 30, message = "메뉴명은 2자이상 30자 미만입니다.")
        private String menu;

        @Max(value = 1000000, message = "금액은 100만원 이하여야 합니다.")
        @Min(value = 1000, message = "금액은 1000원 이상이어야 합니다.")
        private int price;

        @Max(value = 100, message = "수량은 100개 이하여야 합니다.")
        @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
        private int count;

2. 공지사항 만들기 Spring boot 
    2.1 DB 설정
        CREATE TABLE notice(
            noticeno      INT (11)                  NOT NULL AUTO_INCREMENT COMMENT '글 번호',
            title         VARCHAR(300)              NOT NULL COMMENT '제목',
            content       TEXT                      NOT NULL COMMENT '내용',
            wname         VARCHAR (20)              NOT NULL COMMENT '작성자',
            passwd        VARCHAR (20)              NULL COMMENT '패스워드',
            cnt           SMALLINT(10)              NOT NULL DEFAULT '0' COMMENT '조회수',
            rdate         DATETIME                  NOT NULL COMMENT '등록일',
            PRIMARY KEY (noticeno)  
            );
    2.2 LOMBOK
        -  https://projectlombok.org/download 1.18.18 설치 후 더블클릭 - sts 경로에 설치 
    2.3 Spring starter project 
        - Spring Boot DevTools
          LOMBOK
          JDBC API
          MyBatis Framework
          MariaDB driver
          Spring web 

          이것들 추가 
    2.4 add gradle dependency
        	//jsp 사용
            implementation 'javax.servlet:jstl'  
            //mysql connction
            implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'  
            //Annotaion form 검증 
            implementation 'org.springframework.boot:spring-boot-starter-validation'   
            runtimeOnly 'mysql:mysql-connector-java'
    2.5 src/main/webapp/WEB-INF/views 경로 추가 
    2.6 src/main/resources/application.properties 에 추가 

            server.port = 8000
            # JSP View path
            spring.mvc.view.prefix=/WEB-INF/views/
            spring.mvc.view.suffix=.jsp
            # DEVTOOLS (DevToolsProperties)
            spring.devtools.livereload.enabled=true
            # MariaDB
            spring.datasource.hikari.driver-class-name=com.mysql.cj.jdbc.Driver
            spring.datasource.hikari.jdbc-url: jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
            # Hikari Connection Pool
            spring.datasource.hikari.username=root
            spring.datasource.hikari.password=root
            spring.datasource.hikari.connection-test-query=SELECT 1

    2.7 Application.java 에 ComponentScan 설정 
          - @ComponentScan(basePackages = {"com.study.notice","com.study.model"})
    
    2.8 폴더 생성 
        (1) jsp views
        - /src/main/폴더에 webapp/WEB-INF/views 생성
        (2) images 
        - /src/main/resources/static 폴더에 images 생성
          /src/main/resources/mybatis 폴더 생성
    2.9 MyBatis 설정 추가 

        -----------------------------------------------------------------------------
        package com.study.notice;

        import javax.sql.DataSource;

        import org.apache.ibatis.session.SqlSessionFactory;
        import org.mybatis.spring.SqlSessionFactoryBean;
        import org.mybatis.spring.SqlSessionTemplate;
        import org.mybatis.spring.annotation.MapperScan;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.context.properties.ConfigurationProperties;
        import org.springframework.context.ApplicationContext;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.context.annotation.PropertySource;
        
        import com.zaxxer.hikari.HikariConfig;
        import com.zaxxer.hikari.HikariDataSource;

        @Configuration
        @PropertySource("classpath:/application.properties")  // 설정 파일 위치
        @MapperScan(basePackages= {"com.study.model"})
        public class   {
        @Autowired
        private ApplicationContext applicationContext;
        
        @Bean
        @ConfigurationProperties(prefix="spring.datasource.hikari") // 설정 파일의 접두사 선언 
        public HikariConfig hikariConfig() {
            return new HikariConfig();
        }
        
        @Bean
        public DataSource dataSource() throws Exception{
            DataSource dataSource = new HikariDataSource(hikariConfig());
            System.out.println(dataSource.toString());  // 정상적으로 연결 되었는지 해시코드로 확인
            return dataSource;
        }
        
        @Bean
        public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/**/*.xml"));   
            return sqlSessionFactoryBean.getObject();
        }
        
        @Bean
        public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
            return new SqlSessionTemplate(sqlSessionFactory);
        }
        }
        -----------------------------------------------------------------------------

    2.10 Junit4 테스트 
    - /src/test/java 폴더에 테스트 기초 파일이 생성되어 있음
    - 테스트 실행: NoticeApplicationTests.java 파일 선택 -> Debug as -> JUnit test

    2.11 Tiles 설정 build.gradle 에 추가 10번 
    - // https://mvnrepository.com/artifact/org.apache.tiles/tiles-jsp
    implementation group: 'org.apache.tiles', name: 'tiles-jsp', version: '3.0.8'

3. 공지사항 등록, 목록 처리 -> 11번 
    3.1 src/main/resources/mybatis/notice.xml 추가 
        <?xml version="1.0" encoding="UTF-8"?>
            <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
            <mapper namespace="com.study.model.NoticeMapper">
                <!-- 등록 -->
                <insert id="create" parameterType="com.study.model.NoticeDTO">
                    INSERT INTO notice(title, content, wname, passwd, cnt, rdate)
                    VALUES(#{title}, #{content}, #{wname}, #{passwd}, 0, NOW())
                </insert>
                <!-- 목록 -->
                <select id="list" parameterType="Map" resultType="com.study.model.NoticeDTO">
                    SELECT noticeno, title, wname, cnt, rdate
                    FROM notice
                    <where>
                        <choose>
                            <when test="col=='wname'">
                                wname like CONCAT('%',#{word},'%')
                            </when>
                            <when test="col=='title'">
                                title like CONCAT('%',#{word},'%')
                            </when>
                            <when test="col=='content'">
                                content like CONCAT('%',#{word},'%')
                            </when>
                            <when test="col=='title_content'">
                                title like CONCAT('%',#{word},'%')
                                or
                                content like CONCAT('%',#{word},'%')
                            </when>
                        </choose>
                    </where>
                    ORDER BY noticeno DESC
                    limit #{sno} , #{cnt}
                </select>
                <!--total -->
                <select id="total" resultType="int" parameterType="Map">
                    select count(*) from notice
                    <where>
                        <choose>
                            <when test="col=='wname'">
                                wname like CONCAT('%',#{word},'%')
                            </when>
                            <when test="col=='title'">
                                title like CONCAT('%',#{word},'%')
                            </when>
                            <when test="col=='content'">
                                content like CONCAT('%',#{word},'%')
                            </when>
                            <when test="col=='title_content'">
                                title like CONCAT('%',#{word},'%')
                                or
                                content like CONCAT('%',#{word},'%')
                            </when>
                        </choose>
                    </where>
                </select>
            </mapper>

    3.2 NoticeMapper 추가 

    --------------------------------------------------------------------------------------
    package com.study.model;
 
    import org.apache.ibatis.annotations.Mapper;
    
    @Mapper
    public interface NoticeMapper {
    	int create(NoticeDTO dto);
        List<NoticeDTO> list(Map map);
        int total(Map map);
    }

    --------------------------------------------------------------------------------------
            
                
    - Service 인터페이스와 ServiceImpl클래스를 만들어 사용한다.
    - 인터페이스와 구현클래스로 분리하는 장점
        ① 느스한 결합(loose coupling)으로 기능간의 의존관계 최소화
        ② 기능변화에 대한 최소한의 수정에 따른 유연성 최대화
        ③ 모듈화를 통한 높은 재사용성
        ④ 스프링의 IOC/DI 사용의 활용의 극대화

    3.3 NoticeService 추가 

    3.4 NoticeController 에 함수 추가 

    3.5 view 작성 및 tiles.xml 수정 

        -----------------------------------------------------------------------------------
        tiles.xml

        <definition name="/create" extends="main">
            <put-attribute name="title" value="등록"></put-attribute>
            <put-attribute name="body"
            value="/WEB-INF/views/createForm.jsp" />
        </definition>
        <definition name="/list" extends="main">
            <put-attribute name="title" value="목록"></put-attribute>
            <put-attribute name="body"
            value="/WEB-INF/views/list.jsp" />
        </definition>

         -----------------------------------------------------------------------------------

    3.6 new 이미지 생성을 위한 tld - webapp/WEB-INF/tlb/tld-el-functions.tld
        

        <?xml version="1.0" encoding="UTF-8" ?> 
 
        <taglib xmlns="http://java.sun.com/xml/ns/j2ee" 
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
            xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee 
                                web-jsptaglibrary_2_0.xsd" 
            version="2.0"> 
            
            <description>EL 사용</description> 
            <tlib-version>1.0</tlib-version> 
            <uri>/ELFunctions</uri> 
            
            <function>  
                <description>new 이미지 출력</description> 
                <name>newImg</name>  
                <function-class>                   
                    com.study.utility.Utility
                </function-class> 
                <function-signature>    
                    boolean compareDay( java.lang.String ) 
                </function-signature> 
            </function> 
        </taglib> 

        paging 에 들어가있는 것

        <div style='text-align:center'><ul class='pagination'>
        <li class='active'><a href=#>1</a></li>
        <li><a href='./list?col=&word=&nowPage=2'>2</A></li>
        <li><a href='./list?col=&word=&nowPage=3'>3</A></li>
        <li><a href='./list?col=&word=&nowPage=4'>4</A></li>
        <li><a href='./list?col=&word=&nowPage=5'>5</A></li>
        <li><A href='./list?col=&word=&nowPage=6'>다음</A></li></ul>
        </div>
        1
        0
        false

4.  공지사항 조회, 수정, 삭제, 패스워드 검증 처리 [12장]
    4.1 mybatis/notice.xml 쿼리 추가로
    4.2 mapper 에 추가 
    4.3 service interface 추상화 추가 
    4.4 service Implement class 에 구체화 
    4.5 controller 추가 

[2021.03.23 화요일]
1. Amateras ERD
    1.1 Help -> Install New Sorftware -> add
            Name : Amateras Modeler, 
            Location : https://takezoe.github.io/amateras-update-site
    1.2 new -> other -> AmaterasERD -> ER Diagram
    1.3 SQL Dialect : oracle 
                - 드라이버를 lib폴더에서 선택한다.
                - JDBC Driver: oracle.jdbc.driver.OracleDriver
                - Oracle 설정: jdbc:oracle:thin:@localhost:1521:XE
    1.4 테이블 생성 및 참조 그래프 넣기 
    1.5 export -> ddl 
     
        /**********************************/
        /* Table Name: 카테고리그룹 */
        /**********************************/
        CREATE TABLE categrp(
                categrpno                     		NUMBER(10)				 NOT NULL primary key,
                name                          		VARCHAR2(50)			 NOT NULL,
                seqno                         		NUMBER(7)	default 0	 NOT NULL,
                visible                       		CHAR(1)		default 'Y'	 NOT NULL,
                rdate                         		DATE		 			 NOT NULL
        );

        COMMENT ON TABLE categrp is '카테고리그룹';
        COMMENT ON COLUMN categrp.categrpno is '카테고리그룹번호';
        COMMENT ON COLUMN categrp.name is '이름';
        COMMENT ON COLUMN categrp.seqno is '출력순서';
        COMMENT ON COLUMN categrp.visible is '출력모드';
        COMMENT ON COLUMN categrp.rdate is '그룹생성일';

2. 17회차 
    2.1 dbeaver 원래 경로 : C:\Users\JCS\AppData\Roaming\DBeaverData\workspace6\General\Scripts
    2.2 CategrpVO 생성 
            ----------------------------------------
            package com.study.categrp;

            import lombok.Data;

            @Data
            public class CategrpVO {
                /* 카테고리 그룹번호 */
                private int categrpno;
                /** 카테고리 이름 */
                private String name;
                /** 출력 순서 */
                private int seqno;
                /** 출력 모드 */
                private String visible;
                /** 등록일 */
                private String rdate;
            }

            ----------------------------------------
    2.3 ComponentScan 추가 - @ComponentScan(basePackages = {"com.study.*"})
    2.4 Tile 설정 
    --------------------------------------------
            <tiles-definitions>
            <!-- main -->
            <definition name="main" template="/WEB-INF/views/menu/template.jsp">
                <put-attribute name="header" value="/WEB-INF/views/menu/top.jsp" />
                <put-attribute name="footer" value="/WEB-INF/views/menu/bottom.jsp" />
            </definition>
            <definition name="/home" extends="main">
                <put-attribute name="title" value="기본페이지"></put-attribute>
                <put-attribute name="body" value="/WEB-INF/views/index.jsp" />
            </definition>
    --------------------------------------------     
    2.5 categrp 관련 클래스 인터페이스 생성 
        - Mapper interface 
        - service interface 
        - CategrpServiceImpl service interface implement -> @Service
                                                            @Qualifier("com.study.categrp.CategrpServiceImpl")

[2021.03.24 수요일]
1. 테이블 생성 cate
    CREATE TABLE cate(
    cateno                         NUMBER(10)  NOT NULL  PRIMARY KEY,
    categrpno                      NUMBER(10)  NULL,
    name                           VARCHAR2(100)  NOT NULL,
    seqno                          NUMBER(10) default 0 NOT NULL,
    visible                        CHAR(1)  default 'Y'  NOT NULL,
    rdate                          DATE  NOT NULL,
    cnt                            NUMBER(10) default 0  NOT NULL,
    FOREIGN KEY (categrpno) REFERENCES categrp (categrpno)
    );
    
    COMMENT ON TABLE cate is '카테고리';
    COMMENT ON COLUMN cate.cateno is '카테고리번호';
    COMMENT ON COLUMN cate.categrpno is '카테고리그룹번호';
    COMMENT ON COLUMN cate.name is '카테고리이름';
    COMMENT ON COLUMN cate.seqno is '출력순서';
    COMMENT ON COLUMN cate.visible is '출력모드';
    COMMENT ON COLUMN cate.rdate is '등록일';
    COMMENT ON COLUMN cate.cnt is '등록된 글 수';
    
    DROP SEQUENCE cate_seq;
    CREATE SEQUENCE cate_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
    NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
    
    INSERT INTO cate(cateno, categrpno, name, seqno, visible, rdate, cnt)
    VALUES(cate_seq.nextval, 1, '가을', 1, 'Y', sysdate, 0);
    INSERT INTO cate(cateno, categrpno, name, seqno, visible, rdate, cnt)
    VALUES(cate_seq.nextval, 1, '겨울', 1, 'Y', sysdate, 0);
    INSERT INTO cate(cateno, categrpno, name, seqno, visible, rdate, cnt)
    VALUES(cate_seq.nextval, 1, '봄', 1, 'Y', sysdate, 0);
    COMMIT;
    -- 목록
    SELECT cateno, categrpno, name, seqno, visible, rdate, cnt
    FROM cate
    ORDER BY cateno ASC;  
    
    -- 조회
    SELECT cateno, categrpno, name, seqno, visible, rdate, cnt
    FROM cate
    WHERE cateno=3;
    -- 수정
    UPDATE cate
    SET categrpno=1, name='식당', seqno = 10, visible='N', cnt=0
    WHERE cateno = 3;
    commit;
    -- 삭제
    DELETE cate
    WHERE cateno = 3;
    SELECT * FROM cate;
    -- 출력 순서 상향, 10 ▷ 1
    UPDATE cate
    SET seqno = seqno - 1
    WHERE cateno=2;
    SELECT cateno, categrpno, name, seqno, visible, rdate, cnt
    FROM cate
    ORDER BY seqno ASC;
    -- 출력순서 하향, 1 ▷ 10
    UPDATE cate
    SET seqno = seqno + 1
    WHERE cateno=2;
    -- 출력 모드의 변경
    UPDATE cate
    SET visible='Y'
    WHERE cateno=2;
    UPDATE cate
    SET visible='N'
    WHERE cateno=2;
    ---------------------------------------------         
    -- FK를 갖는 테이블 추가 구현
    ---------------------------------------------
    -- 카테고리 그룹에따른 카테고리 목록
    SELECT cateno, categrpno, name, seqno, visible, rdate, cnt
    FROM cate
    WHERE categrpno=1
    ORDER BY seqno ASC;
    -- 부모 테이블 레코드 삭제(오류)
    DELETE FROM categrp
    WHERE categrpno = 1;
    
    -- 삭제하려면 레코드의 categrpno가 어디에서 쓰이는지 알려주어야함.
    SELECT COUNT(*) as cnt
    FROM cate
    WHERE categrpno=1;
    
            
    -- 자식 테이블에서 FK가 1인 레코드 모두 삭제
    DELETE FROM cate
    WHERE categrpno=1;
    -- 부모 테이블 레코드 삭제
    DELETE FROM categrp
    WHERE categrpno=1;
    commit;
    
    -- 부모 테이블 레코드 삭제 확인
    SELECT * FROM categrp ORDER BY categrpno ASC;
    
    SELECT r.categrpno as r_categrpno, r.name as r_name,
        c.cateno, c.categrpno, c.name, c.seqno, c.visible, c.rdate, c.cnt
    FROM categrp r, cate c
    WHERE r.categrpno = c.categrpno
    ORDER BY r.categrpno ASC, c.seqno ASC; -- inner 조인 
    
    -- 통합 VO, categrpno 별 cate 목록
    SELECT r.categrpno as r_categrpno, r.name as r_name,
        c.cateno, c.categrpno, c.name, c.seqno, c.visible, c.rdate, c.cnt
    FROM categrp r, cate c
    WHERE (r.categrpno = c.categrpno) AND r.categrpno=1
    ORDER BY r.categrpno ASC, c.seqno ASC;
    
    -- contents 추가에따른 등록된 글수의 증가
    UPDATE cate 
    SET    cnt = cnt + 1 
    WHERE  cateno=1;
    -- contents 추가에따른 등록된 글수의 감소
    UPDATE cate 
    SET    cnt = cnt - 1 
    WHERE  cateno=1; 
    -- 글수 초기화
    UPDATE cate
    SET    cnt = 0;
    
    COMMIT;
    
2. CateVO class 생성 
    package com.study.cate;
 
    import lombok.Data;
    
    @Data
    public class CateVO { 
    private int cateno;  
    private int categrpno;
    private String name;
    private int seqno;
    private String visible;
    private String rdate;
    private int cnt;
    }

3.cate.xml 에 추가 mybatis 
 
>>> mybaits/cate.xml
<?xml version="1.0" encoding="UTF-8"?>
 
    <!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    
    <mapper namespace="com.study.cate.CateMapper">
    <insert id="create" parameterType="com.study.cate.CateVO">
        INSERT INTO cate(cateno, categrpno, name, seqno, visible, rdate, cnt)
        VALUES(cate_seq.nextval, #{categrpno}, #{name}, #{seqno}, #{visible}, sysdate, 0)
    </insert> 
    
    <!-- categrpno 별 cate 목록: categrp + cate inner join,  1 : 다, 통합 VO -->
    <select id="list_join_by_categrpno" resultType="com.study.cate.Categrp_Cate_join" parameterType="int">
        SELECT r.categrpno as r_categrpno, r.name as r_name,
                c.cateno, c.categrpno, c.name, c.seqno, c.visible, c.rdate, c.cnt
        FROM categrp r, cate c
        WHERE (r.categrpno = c.categrpno) AND r.categrpno=#{categrpno}
        ORDER BY r.categrpno ASC, c.seqno ASC
    </select>
    
    <select id="read" resultType="com.study.cate.CateVO" parameterType="int">
        SELECT cateno, categrpno, name, seqno, visible, rdate, cnt
        FROM cate
        WHERE cateno=#{cateno}
    </select>  
    
    <update id="update" parameterType="com.study.cate.CateVO">
        UPDATE cate
        SET categrpno=#{categrpno}, name=#{name}, seqno=#{seqno}, visible=#{visible}, cnt=#{cnt}
        WHERE cateno = #{cateno}
    </update>
    
    <!-- 삭제, return: int -->
    <delete id="delete" parameterType="int">
        DELETE cate
        WHERE cateno = #{cateno}
    </delete>       
    
    <!-- 우선순위 상향 up 10 ▷ 1 -->
    <update id="update_seqno_up" parameterType="int">
        UPDATE cate
        SET seqno = seqno - 1
        WHERE cateno=#{cateno}
    </update>
    
    <!-- 우선순위 하향 down 1 ▷ 10 -->
    <update id="update_seqno_down" parameterType="int">
        UPDATE cate
        SET seqno = seqno + 1
        WHERE cateno=#{cateno}
    </update>
    
    <update id="update_visible" parameterType="com.study.cate.CateVO">
        UPDATE cate
        SET visible=#{visible}
        WHERE cateno=#{cateno}
    </update>
    
    <select id="list_by_categrpno" resultType="com.study.cate.CateVO" parameterType="int">
        SELECT cateno, categrpno, name, seqno, visible, rdate, cnt
        FROM cate
        WHERE categrpno=#{categrpno}
        ORDER BY seqno ASC
    </select>
    
    
    <update id="increaseCnt" parameterType="int">
        UPDATE cate 
        SET cnt = cnt + 1 
        WHERE cateno=#{cateno}
    </update>
    
    <update id="decreaseCnt" parameterType="int">
        UPDATE cate 
        SET cnt = cnt - 1 
        WHERE cateno=#{cateno}
    </update>
    
    </mapper>

4. CateMapper.java

    package com.study.cate;
    
    import java.util.List;
    
    public interface CateMapper {
    int create(CateVO cateVO);
    
    List<Categrp_Cate_join> list_join_by_categrpno(int categrpno);
    
    CateVO read(int cateno);
    
    int update(CateVO cateVO);
    
    int delete(int cateno);
    
    int update_seqno_up(int cateno);
    
    int update_seqno_down(int cateno);
    
    int update_visible(CateVO cateVO);
    
    List<CateVO> list_by_categrpno(int categrpno);
    
    int increaseCnt(int cateno);
    
    int decreaseCnt(int cateno);
    
    }

5. Service Interface, impl Class 구현
    >>> CateService.java
    public interface CateService {
    int create(CateVO cateVO);
    
    List<Categrp_Cate_join> list_join_by_categrpno(int categrpno);
    
    CateVO read(int cateno);
    
    int update(CateVO cateVO);
    
    int delete(int cateno);
    
    int update_seqno_up(int cateno);
    
    int update_seqno_down(int cateno);
    
    int update_visible(CateVO cateVO);
    
    List<CateVO> list_by_categrpno(int categrpno);
    
    int increaseCnt(int cateno);
    
    int decreaseCnt(int cateno);
    
    }

6.CateServiceImpl.java

    package com.study.cate;
    
    import java.util.List;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    
    
    @Service
    public class CateServiceImpl implements CateService {
    
    @Autowired
    private CateMapper mapper;
    
    @Override
    public int create(CateVO cateVO) {
        int cnt = this.mapper.create(cateVO);
        return cnt;
    }
    
    @Override
    public List<Categrp_Cate_join> list_join_by_categrpno(int categrpno) {
        List<Categrp_Cate_join> list = this.mapper.list_join_by_categrpno(categrpno);
        return list;
    }
    
    @Override
    public CateVO read(int cateno) {
        CateVO cateVO = this.mapper.read(cateno);
        return cateVO;
    }
    
    @Override
    public int update(CateVO cateVO) {
        int cnt = this.mapper.update(cateVO);
        return cnt;
    }
    
    @Override
    public int delete(int cateno) {
        int cnt = this.mapper.delete(cateno);
        return cnt;
    }
    
    @Override
    public int update_seqno_up(int cateno) {
        int cnt = this.mapper.update_seqno_up(cateno);
        return cnt;
    }
    
    @Override
    public int update_seqno_down(int cateno) {
        int cnt = this.mapper.update_seqno_down(cateno);
        return cnt;
    }
    
    @Override
    public int update_visible(CateVO cateVO) {
        if (cateVO.getVisible().equalsIgnoreCase("Y")) {
        cateVO.setVisible("N");
        } else {
        cateVO.setVisible("Y");
        }
        
        int cnt = this.mapper.update_visible(cateVO);
        return cnt;
    }
    
    @Override
    public List<CateVO> list_by_categrpno(int categrpno) {
        List<CateVO> list = this.mapper.list_by_categrpno(categrpno);
        return list;
    }
    
    @Override
    public int increaseCnt(int cateno) {
        int cnt = this.mapper.increaseCnt(cateno);
        return cnt;
    }
    
    @Override
    public int decreaseCnt(int cateno) {
        int cnt = this.mapper.decreaseCnt(cateno);
        return cnt;
    }
    
    }

7. Controller Class 구현
    >>> CateCont.java
    package com.study.cate;
    
    import java.util.ArrayList;
    import java.util.List;
    
    import javax.servlet.http.HttpServletRequest;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;
    import org.springframework.web.servlet.ModelAndView;
    import com.study.categrp.CategrpServiceImpl;
    import com.study.categrp.CategrpVO;
    
    @Controller
    public class CateCont {
    @Autowired
    private CategrpServiceImpl gservice;
    
    @Autowired
    private CateServiceImpl service;
    
    public CateCont() {
        System.out.println("--> CateCont created.");
    }
    
    @RequestMapping(value = "/cate/msg", method = RequestMethod.GET)
    public ModelAndView msg(String url) {
        ModelAndView mav = new ModelAndView();
    
        mav.setViewName(url);
    
        return mav;
    }
    
    @RequestMapping(value = "/cate/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cate/create");
    
        return mav;
    }
    
    @RequestMapping(value = "/cate/create", method = RequestMethod.POST)
    public ModelAndView create(CateVO cateVO) {
        ModelAndView mav = new ModelAndView();
    
        int cnt = this.service.create(cateVO);
        mav.addObject("cnt", cnt);
        mav.addObject("categrpno", cateVO.getCategrpno());
        mav.addObject("url", "/cate/create_msg"); 
    
        mav.setViewName("redirect:/cate/msg");
    
        return mav;
    }
    
    @RequestMapping(value = "/cate/list", method = RequestMethod.GET)
    public ModelAndView list_join_by_categrpno(int categrpno) {
        ModelAndView mav = new ModelAndView();
    
        CategrpVO categrpVO = this.gservice.read(categrpno);
        mav.addObject("categrpVO", categrpVO);
    
        List<Categrp_Cate_join> list = this.service.list_join_by_categrpno(categrpno);
        mav.addObject("list", list); 
    
        mav.setViewName("/cate/list_join_by_categrpno"); 
        return mav;
    }
    
    @RequestMapping(value = "/cate/read_update", method = RequestMethod.GET)
    public ModelAndView read_update(int cateno, int categrpno) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cate/read_update"); // read_update.jsp
    
        CateVO cateVO = this.service.read(cateno);
        mav.addObject("cateVO", cateVO);
    
        List<CateVO> list = this.service.list_by_categrpno(categrpno);
        mav.addObject("list", list);
    
        return mav; 
    }
    
    @RequestMapping(value = "/cate/update", method = RequestMethod.POST)
    public ModelAndView update(CateVO cateVO) {
        ModelAndView mav = new ModelAndView();
    
        int cnt = this.service.update(cateVO);
        mav.addObject("cnt", cnt); 
        mav.addObject("categrpno", cateVO.getCategrpno());
        mav.addObject("url", "/cate/update_msg"); 
    
        mav.setViewName("redirect:/cate/msg");
    
        return mav;
    }
    
    
    @RequestMapping(value = "/cate/read_delete", method = RequestMethod.GET)
    public ModelAndView read_delete(int cateno, int categrpno) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cate/read_delete"); // read_delete.jsp
    
        CateVO cateVO = this.service.read(cateno);
        mav.addObject("cateVO", cateVO);
    
        List<CateVO> list = this.service.list_by_categrpno(categrpno);
        mav.addObject("list", list);
    
        return mav; 
    }
    
    
    @RequestMapping(value = "/cate/delete", method = RequestMethod.POST)
    public ModelAndView delete(int cateno, int categrpno) {
        ModelAndView mav = new ModelAndView();
    
        int cnt = this.service.delete(cateno);
        mav.addObject("cnt", cnt); 
        mav.addObject("categrpno", categrpno);
        mav.addObject("url", "/cate/delete_msg"); 
    
        mav.setViewName("redirect:/cate/msg");
    
        return mav;
    }
    
    @RequestMapping(value = "/cate/update_seqno_up", method = RequestMethod.GET)
    public ModelAndView update_seqno_up(int cateno, int categrpno) {
        ModelAndView mav = new ModelAndView();
    
        int cnt = this.service.update_seqno_up(cateno);
    
        mav.setViewName("redirect:/cate/list?categrpno=" + categrpno); 
    
        return mav;
    }
    
    
    @RequestMapping(value = "/cate/update_seqno_down", method = RequestMethod.GET)
    public ModelAndView update_seqno_down(int cateno, int categrpno) {
        ModelAndView mav = new ModelAndView();
    
        int cnt = this.service.update_seqno_down(cateno);
    
    
        mav.setViewName("redirect:/cate/list?categrpno=" + categrpno); // 
    
        return mav;
    }
    
    @RequestMapping(value = "/cate/update_visible", method = RequestMethod.GET)
    public ModelAndView update_visible(CateVO cateVO) {
        ModelAndView mav = new ModelAndView();
    
        int cnt = this.service.update_visible(cateVO);
        mav.addObject("cnt", cnt); 
    
        if (cnt == 1) {
        mav.setViewName("redirect:/cate/list?categrpno=" + cateVO.getCategrpno());
        } else {
        CateVO vo = this.service.read(cateVO.getCateno());
        String name = vo.getName();
        mav.addObject("name", name);
        mav.setViewName("/cate/update_visible_msg"); // /cate/update_visible_msg.jsp
        }
        return mav;
    }
    
    @RequestMapping(value = "/cate/list_index_left", method = RequestMethod.GET)
    public ModelAndView list_index_left(HttpServletRequest request) {
        // System.out.println("--> list_index() GET called.");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/cate/list_index_left");
    
        List<CategrpVO> categrp_list = gservice.list_seqno_asc();
    
        ArrayList<String> name_title_list = new ArrayList<String>();
    
        StringBuffer url = new StringBuffer();
    
        for (int index = 0; index < categrp_list.size(); index++) {
        CategrpVO categrpVO = categrp_list.get(index);
    
        name_title_list.add("<LI class='categrp_name'>" + categrpVO.getName() + "</LI>");
    
        List<Categrp_Cate_join> cate_list = service.list_join_by_categrpno(categrpVO.getCategrpno());
    
        for (int j = 0; j < cate_list.size(); j++) {
            Categrp_Cate_join categrp_Cate_join = cate_list.get(j);
    
            String name = categrp_Cate_join.getName();
            int cnt = categrp_Cate_join.getCnt();
    
            url.append("<LI class='cate_name'>");
            url.append(
                "  <A href='" + request.getContextPath() + "/contents/list?cateno=" + categrp_Cate_join.getCateno() + "'>");
            url.append(name);
            url.append("  </A>");
            url.append("  <span style='font-size: 0.9em; color: #555555;'>(" + cnt + ")</span>");
            url.append("</LI>");
    
            name_title_list.add(url.toString());
    
            url.delete(0, url.toString().length());
    
        }
        }
    
        mav.addObject("name_title_list", name_title_list);
    
        return mav;
    }
    
    }

8. tiles 

     TilesConfiguration.java 에서 tiles_cate.xml 추가
>>> TilesConfiguration.java

     @Bean
  public TilesConfigurer tilesConfigurer() {
      final TilesConfigurer configurer = new TilesConfigurer();
      //해당 경로에 tiles.xml 파일을 넣음
      configurer.setDefinitions(new String[]{"classpath:/tiles/tiles.xml",
                                             "classpath:/tiles/tiles_cate.xml",
                                             });
      configurer.setCheckRefresh(true);
      return configurer;
  }

  tiles_cate.xml파일 <definition>추가
>>> tiles_cate.xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE tiles-definitions PUBLIC
       "-//Apache Software Foundation//DTD Tiles Configuration 3.0//EN"
       "http://tiles.apache.org/dtds/tiles-config_3_0.dtd">
 
<tiles-definitions>
   <definition name="/cate/create" extends="main">
    <put-attribute name="title" value="카테고리등록"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/create.jsp" />
  </definition>
 <definition name="/cate/create_msg" extends="main">
    <put-attribute name="title" value="등록알림"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/create_msg.jsp" />
  </definition>
 <definition name="/cate/list" extends="main">
    <put-attribute name="title" value="카테고리목록"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/list.jsp" />
  </definition>
 <definition name="/cate/list_join" extends="main">
    <put-attribute name="title" value="list_join"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/list_join.jsp" />
  </definition>
 <definition name="/cate/list_all" extends="main">
    <put-attribute name="title" value="list_all"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/list_all.jsp" />
  </definition>
 <definition name="/cate/list_join_by_categrpno" extends="main">
    <put-attribute name="title" value="list_all"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/list_join_by_categrpno.jsp" />
  </definition>
 <definition name="/cate/read_update" extends="main">
    <put-attribute name="title" value="수정"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/read_update.jsp" />
  </definition>
 <definition name="/cate/update_msg" extends="main">
    <put-attribute name="title" value="수정알림"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/update_msg.jsp" />
  </definition>
 <definition name="/cate/read_delete" extends="main">
    <put-attribute name="title" value="삭제"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/read_delete.jsp" />
  </definition>
 <definition name="/cate/delete_msg" extends="main">
    <put-attribute name="title" value="삭제알림"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/delete_msg.jsp" />
  </definition>
 <definition name="/cate/update_visible_msg" extends="main">
    <put-attribute name="title" value="출력형식"></put-attribute>
    <put-attribute name="body"
      value="/WEB-INF/views/cate/update_visible_msg.jsp" />
  </definition>
 
</tiles-definitions>



9.users 테이블 

    9.1. gradle json 의존성 추가 
-   	//json
        // https://mvnrepository.com/artifact/org.json/json
        implementation group: 'org.json', name: 'json', version: '20201115'  

    9.2 create.jsp 

[2021.03.25 목요일]
1. 문제점 - 로그인 해야 등록이 잘됨 
         - 그림 파일 사이즈 exception 해줘아 함 
         - 삭제 -> 갤러리형 오류 
         - 등록된 게 있는 카테고리 그룹 삭제 시 오류 - child data delete 


**개인 프로젝트 
 - 404 에러 : template.jsp를 찾을 수 없다 -> tomcat clean / project clean / web deployment assembly(클래스없다고 오류뜰때) maven 추가/



[2021.03.29 금요일]
1. spring mvc 생성 - webtest 참조 
    1.1 프로젝트 생성 other -> Spring/Spring Legacy Project -> Spring MVC Project -> com.project.test 입력 
    1.2 pom.xml : java version 1.8, spring 4.3.14
    1.3 WEB-INF/lib 폴더 생성 
    1.4 ojdbc8.jar lib에 넣기 
    1.5 transaction delete/aop,cglib, mybatis, dbcp setting, jackson, tile2, fileupload pom.xml 에 가져오기 
    1.6 web.xml filter
    1.7 servlet-context.xml : interceptor, transaction manager(service의 delete와 이름 매칭시키기)                                  jpa........
    1.8 root-context.xml : db 접속 datasource, mybatis, sqlSessionFactory, mapper, multipartResolver, tiles 
    1.9 src/main/resources/mybatis 폴더 생성
    1.10 WEB-INF/spring/tiles/~~.xml 파일 생성 

today to do list
1. spring boot + vue.js 연동 
    - https://mr-spock.tistory.com/3
    - svn으로 소스관리 그래서 --no-git 으로 한단다 
    - 프로젝트 / web 폴더 생성 
    - vue create vue --no-git 
    - web/vue/vue.config.js 파일 생성 
    - vue 로 가서 npm run build 명령어 빌드 
    - 오류 https://fntg.tistory.com/193
    - npm run serve
2. cgv 프로젝트 분석해서 내 프로젝트에 반영하기 영화검색 



[2021.04.01 목요일]
1. dbeaver 에서 oracle sysdba 로 로그인 
    - id : sys as sysdba, password : sd9568
2. 스키마 변경
    - ALTER SESSION SET CURRENT_SCHEMA = 스키마명;

[2021.04.26 월요일]
1.github 블로그 만들기 글쓰기 

[2021.04.27 화요일]
1. sts spring boot + vue.js + mysql 환경 설정하기 
https://simplevue.gitbook.io/intro/01.-vue-cli
    - npm install -g vue-cli@2.9.3
    - vue cli 버전 확인 : vue --version 
    - vue 버전 확인 : 
    - npm install request
    - npm install path
    Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.

    Reason: Failed to determine a suitable driver class
