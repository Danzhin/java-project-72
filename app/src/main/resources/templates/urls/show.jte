@import hexlet.code.utils.LocalDateTimeFormatter
@import hexlet.code.utils.NamedRoutes
@param hexlet.code.dto.urls.UrlPage page 

@template.layout.page(
    content = @`
        <table>
            <tbody>
                <tr>
                    <td>Id</td>
                    <td>${page.getUrl().getId()}</td>
                </tr>
                <tr>
                    <td>Имя</td>
                    <td>${page.getUrl().getName()}</td>
                </tr>
                <tr>
                    <td>Дата создания</td>
                    <td>${LocalDateTimeFormatter.toString(page.getUrl().getCreatedAt())}</td>
                </tr>
            </tbody>
        </table>
        <form action="${NamedRoutes.urlCheckPath(page.getUrl().getId())}" method="post">
            <button type="submit">Проверить</button>
        </form>
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Код ответа</th>
                    <th>title</th>
                    <th>h1</th>
                    <th>description</th>
                    <th>Дата проверки</th>
                </tr>
            </thead>
            <tbody>
                @for(var urlCheck : page.getUrlChecks())
                    <tr>
                        <td>${urlCheck.getId()}</td>
                        <td>${urlCheck.getStatusCode()}</td>
                        <td>${urlCheck.getTitle()}</td>
                        <td>${urlCheck.getDescription()}</td>
                        <td>${LocalDateTimeFormatter.toString(urlCheck.getCreatedAt())}</td>
                    </tr>
                @endfor
            </tbody>
        </table>
    `
)