package gg.jte.generated.ondemand.layout;
import hexlet.code.utils.Routes;
@SuppressWarnings("unchecked")
public final class JtepageGenerated {
	public static final String JTE_NAME = "layout/page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,28,28,28,28,28,28,28,28,28,28,29,29,29,29,29,29,29,29,29,36,36,37,37,37,37,38,38,38,41,41,44,44,44,58,58,58,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, gg.jte.Content content, hexlet.code.pages.BasePage page) {
		jteOutput.writeContent("\r\n<!doctype html>\r\n<html lang=\"en\">\r\n    <head>\r\n        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n        <title>Анализатор страниц</title>\r\n        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\r\n              integrity=\"sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65\" crossorigin=\"anonymous\">\r\n        <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js\"\r\n                integrity=\"sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4\"\r\n                crossorigin=\"anonymous\"></script>\r\n    </head>\r\n    <body class=\"d-flex flex-column min-vh-100\">\r\n        <nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\r\n            <div class=\"container-fluid\">\r\n                <a class=\"navbar-brand\" href=\"/\">Анализатор страниц</a>\r\n\r\n                <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNav\"\r\n                        aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\r\n                    <span class=\"navbar-toggler-icon\"></span>\r\n                </button>\r\n\r\n                <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\r\n                    <div class=\"navbar-nav\">\r\n                        <a class=\"nav-link\"");
		var __jte_html_attribute_0 = Routes.ROOT_PATH;
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" href=\"");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(__jte_html_attribute_0);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(">Главная</a>\r\n                        <a class=\"nav-link\"");
		var __jte_html_attribute_1 = Routes.URLS_PATH;
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
			jteOutput.writeContent(" href=\"");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(__jte_html_attribute_1);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(">Сайты</a>\r\n                    </div>\r\n                </div>\r\n            </div>\r\n        </nav>\r\n\r\n        <main class=\"flex-grow-1\">\r\n            ");
		if (page.getFlash().getMassage() != null) {
			jteOutput.writeContent("\r\n                <div class=\"rounded-0 m-0 alert alert-dismissible fade show ");
			jteOutput.setContext("div", "class");
			jteOutput.writeUserContent(page.getFlash().getAlertType());
			jteOutput.setContext("div", null);
			jteOutput.writeContent("\" role=\"alert\">\r\n                    <p class=\"m-0\">");
			jteOutput.setContext("p", null);
			jteOutput.writeUserContent(page.getFlash().getMassage());
			jteOutput.writeContent("</p>\r\n                    <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n                </div>\r\n            ");
		}
		jteOutput.writeContent("\r\n\r\n            <section>\r\n                ");
		jteOutput.setContext("section", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\r\n            </section>\r\n        </main>\r\n\r\n        <footer class=\"footer border-top py-3 mt-5 bg-light\">\r\n            <div class=\"container-xl\">\r\n                <div class=\"text-center\">\r\n                    created by\r\n                    <a href=\"https://ru.hexlet.io\" target=\"_blank\">Hexlet</a>\r\n                </div>\r\n            </div>\r\n        </footer>\r\n\r\n    </body>\r\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		gg.jte.Content content = (gg.jte.Content)params.get("content");
		hexlet.code.pages.BasePage page = (hexlet.code.pages.BasePage)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, content, page);
	}
}
