package com.pdwu.easycloud.file.service.impl;

import com.pdwu.easycloud.file.service.IShortLinkService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml"})
public class ShortLinkServiceImplTest {

    @Autowired
    private ShortLinkServiceImpl shortLinkService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getShortLink() throws Exception {
        ShortLinkServiceImpl.setArrayToMine(); //自定的62进制排列
        ShortLinkServiceImpl.setArrayToStandard();//标准排列


        assertEquals("0", shortLinkService.getShortLink(0L));
        assertEquals("1", shortLinkService.getShortLink(1L));
        assertEquals("Z", shortLinkService.getShortLink(61L));
        assertEquals("10", shortLinkService.getShortLink(62L));
        assertEquals("11", shortLinkService.getShortLink(63L));
        assertEquals("g8", shortLinkService.getShortLink(1000L));
        assertEquals("3d7", shortLinkService.getShortLink(12345L));
        assertEquals("2zix", shortLinkService.getShortLink(612345L));
        assertEquals("5ban", shortLinkService.getShortLink(1234567L));

    }

    @Test
    public void getShareId() throws Exception {
        ShortLinkServiceImpl.setArrayToStandard();

        assertEquals(0L, shortLinkService.getShareId("").longValue());
        assertEquals(0L, shortLinkService.getShareId("0").longValue());
        assertEquals(1L, shortLinkService.getShareId("1").longValue());
        assertEquals(61L, shortLinkService.getShareId("Z").longValue());
        assertEquals(62L, shortLinkService.getShareId("10").longValue());
        assertEquals(63L, shortLinkService.getShareId("11").longValue());
        assertEquals(1000L, shortLinkService.getShareId("g8").longValue());
        assertEquals(12345L, shortLinkService.getShareId("3d7").longValue());
        assertEquals(612345L, shortLinkService.getShareId("2zix").longValue());
        assertEquals(1234567L, shortLinkService.getShareId("5ban").longValue());

    }

}