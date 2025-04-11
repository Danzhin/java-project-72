@import hexlet.code.utils.Routes
@param hexlet.code.pages.urls.UrlPage page

@template.layout.page(
    page = page,
    content = @`
        <div class="container-lg mt-5">
            <h1>Сайт: ${page.getUrl().getName()}</h1>

            <table class="table table-bordered table-hover mt-3">
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
                        <td>${page.getUrl().getCreatedAt()}</td>
                    </tr>
                </tbody>
            </table>

            <h2 class="mt-5">Проверки</h2>
            <form method="post" action="${Routes.urlCheckPath(page.getUrl().getId())}">
                <button type="submit" class="btn btn-primary">Запустить проверку</button>
            </form>

            <table class="table table-bordered table-hover mt-3">
                <thead>
                    <th>Id</th>
                    <th>Код ответа</th>
                    <th>title</th>
                    <th>h1</th>
                    <th>description</th>
                    <th>Дата проверки</th>
                </thead>
                <tbody>
                @for(var urlCheck : page.getChecks())
                    <tr>
                        <td>${urlCheck.getId()}</td>
                        <td>${urlCheck.getStatusCode()}</td>
                        <td>${urlCheck.getTitle()}</td>
                        <td>${urlCheck.getH1()}</td>
                        <td>${urlCheck.getDescription()}</td>
                        <td>${urlCheck.getCreatedAt()}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </div>
    `
)