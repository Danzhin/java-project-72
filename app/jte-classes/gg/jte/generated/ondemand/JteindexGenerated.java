package gg.jte.generated.ondemand;
import hexlet.code.utils.Routes;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,3,3,6,6,12,12,12,12,12,12,12,12,12,30,30,30,30,30,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, hexlet.code.models.pages.BasePage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n        <div class=\"container-fluid bg-dark p-5\">\r\n            <div class=\"row\">\r\n                <div class=\"col-md-10 col-lg-8 mx-auto text-white\">\r\n                    <h1 class=\"display-3 mb-0\">Анализатор страниц</h1>\r\n                    <p class=\"lead\">Бесплатно проверяйте сайты на SEO пригодность</p>\r\n                    <form");
				var __jte_html_attribute_0 = Routes.URLS_PATH;
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" method=\"post\" class=\"rss-form text-body\">\r\n                        <div class=\"row\">\r\n                            <div class=\"col\">\r\n                                <div class=\"form-floating\">\r\n                                    <input id=\"url-input\" autofocus type=\"text\" required name=\"url\" aria-label=\"url\"\r\n                                           class=\"form-control w-100\" placeholder=\"ссылка\" autocomplete=\"off\">\r\n                                    <label for=\"url-input\">Ссылка</label>\r\n                                </div>\r\n                            </div>\r\n                            <div class=\"col-auto\">\r\n                                <button type=\"submit\" class=\"h-100 btn btn-lg btn-primary px-sm-5\">Проверить</button>\r\n                            </div>\r\n                        </div>\r\n                    </form>\r\n                    <p class=\"mt-2 mb-0 text-muted\">Пример: https://www.example.com</p>\r\n                </div>\r\n            </div>\r\n        </div>\r\n    ");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		hexlet.code.models.pages.BasePage page = (hexlet.code.models.pages.BasePage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
