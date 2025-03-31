package gg.jte.generated.ondemand.urls;
import hexlet.code.utils.NamedRoutes;
@SuppressWarnings("unchecked")
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,3,3,6,6,8,8,8,14,14,14,18,18,18,22,22,22,28,28,28,28,28,28,28,28,28,42,42,44,44,44,45,45,45,46,46,46,47,47,47,48,48,48,49,49,49,51,51,55,55,55,55,55,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, hexlet.code.dto.urls.UrlPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n        <div class=\"container-lg mt-5\">\r\n            <h1>Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\r\n\r\n            <table class=\"table table-bordered table-hover mt-3\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td>Id</td>\r\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\r\n                    </tr>\r\n                    <tr>\r\n                        <td>Имя</td>\r\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\r\n                    </tr>\r\n                    <tr>\r\n                        <td>Дата создания</td>\r\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getCreatedAt());
				jteOutput.writeContent("</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n\r\n            <h2 class=\"mt-5\">Проверки</h2>\r\n            <form method=\"post\"");
				var __jte_html_attribute_0 = NamedRoutes.urlCheckPath(page.getUrl().getId());
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\r\n                <button type=\"submit\" class=\"btn btn-primary\">Запустить проверку</button>\r\n            </form>\r\n\r\n            <table class=\"table table-bordered table-hover mt-3\">\r\n                <thead>\r\n                    <th>Id</th>\r\n                    <th>Код ответа</th>\r\n                    <th>title</th>\r\n                    <th>h1</th>\r\n                    <th>description</th>\r\n                    <th>Дата проверки</th>\r\n                </thead>\r\n                <tbody>\r\n                ");
				for (var urlCheck : page.getChecks()) {
					jteOutput.writeContent("\r\n                    <tr>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getId());
					jteOutput.writeContent("</td>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getStatusCode());
					jteOutput.writeContent("</td>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getTitle());
					jteOutput.writeContent("</td>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getH1());
					jteOutput.writeContent("</td>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getDescription());
					jteOutput.writeContent("</td>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getCreatedAt());
					jteOutput.writeContent("</td>\r\n                    </tr>\r\n                ");
				}
				jteOutput.writeContent("\r\n                </tbody>\r\n            </table>\r\n        </div>\r\n    ");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		hexlet.code.dto.urls.UrlPage page = (hexlet.code.dto.urls.UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
