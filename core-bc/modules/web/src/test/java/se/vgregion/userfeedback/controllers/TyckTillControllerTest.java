package se.vgregion.userfeedback.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.*;
import se.vgregion.userfeedback.domain.FormTemplate;
import se.vgregion.userfeedback.domain.StaticCategory;
import se.vgregion.userfeedback.domain.StaticCategoryRepository;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/web/WEB-INF/TyckTill-servlet-config.xml"})
public class TyckTillControllerTest {
    @Inject
    private ApplicationContext applicationContext;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private HandlerAdapter handlerAdapter;


    @Before
    public void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        handlerAdapter = applicationContext.getBean(HandlerAdapter.class);
    }

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final HandlerMapping handlerMapping = applicationContext.getBean(HandlerMapping.class);
        final HandlerExecutionChain handler = handlerMapping.getHandler(request);
        assertNotNull("No handler found for request, check you request mapping", handler);

        final Object controller = handler.getHandler();
        // if you want to override any injected attributes do it here

        final HandlerInterceptor[] interceptors =
                handlerMapping.getHandler(request).getInterceptors();
        for (HandlerInterceptor interceptor : interceptors) {
            final boolean carryOn = interceptor.preHandle(request, response, controller);
            if (!carryOn) {
                return null;
            }
        }

        final ModelAndView mav = handlerAdapter.handle(request, response, controller);
        return mav;
    }


    @Test
    public void testSetupForm_FormTemplate() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/KontaktaOss");
        final ModelAndView mav = handle(request, response);

        assertEquals("KontaktaOss", mav.getViewName());

        Map<String, Object> model = mav.getModel();

        FormTemplate template = (FormTemplate) model.get("template");
        assertEquals("default", template.getName());
    }

    @Test
    public void testSetupForm_FormTemplate_default() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/KontaktaOss");
        request.setParameter("formName", "default");
        final ModelAndView mav = handle(request, response);

        assertEquals("KontaktaOss", mav.getViewName());

        Map<String, Object> model = mav.getModel();

        FormTemplate template = (FormTemplate) model.get("template");
        assertEquals("default", template.getName());
    }

    @Test
    public void testSetupForm_FormTemplate_test() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/KontaktaOss");
        request.setParameter("formName", "test");
        final ModelAndView mav = handle(request, response);

        assertEquals("KontaktaOss", mav.getViewName());

        Map<String, Object> model = mav.getModel();

        FormTemplate template = (FormTemplate) model.get("template");
        assertEquals("test", template.getName());
    }

    @Test
    public void testSetupForm_FormTemplate_wrong() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/KontaktaOss");
        request.setParameter("formName", "wrong");
        final ModelAndView mav = handle(request, response);

        assertEquals("KontaktaOss", mav.getViewName());

        Map<String, Object> model = mav.getModel();

        FormTemplate template = (FormTemplate) model.get("template");
        assertEquals("default", template.getName());
    }


    @Test
    public void testSetupForm_StaticCategory() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/KontaktaOss");
        final ModelAndView mav = handle(request, response);

        Map<String, Object> model = mav.getModel();
        StaticCategory category = (StaticCategory) model.get("contentCategory");
        assertEquals("Webbplatsens innehåll", category.getName());
        assertEquals(0, category.getSubCategories().size());
        assertEquals(StaticCategoryRepository.STATIC_CONTENT_CATEGORY, category.getId());

        category = (StaticCategory) model.get("functionCategory");
        assertEquals("Webbplatsens funktion", category.getName());
        assertEquals(0, category.getSubCategories().size());
        assertEquals(StaticCategoryRepository.STATIC_FUNCTION_CATEGORY, category.getId());

        category = (StaticCategory) model.get("otherCategory");
        assertEquals("Övrigt", category.getName());
        assertEquals(0, category.getSubCategories().size());
        assertEquals(StaticCategoryRepository.STATIC_OTHER_CATEGORY, category.getId());
    }

    @Test
    public void testSendUserFeedback() throws Exception {

    }

    @Test
    public void testGetReportService() throws Exception {

    }

    @Test
    public void testSetReportService() throws Exception {

    }
}
