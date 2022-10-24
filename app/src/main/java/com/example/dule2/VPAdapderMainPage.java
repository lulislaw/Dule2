package com.example.dule2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dule2.Pages.HomeActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class VPAdapderMainPage extends RecyclerView.Adapter<VPAdapderMainPage.ViewHolder> {

    ArrayList<ViewPagerItemMainPage> viewPagerItemMainPageArrayList;


    public VPAdapderMainPage(ArrayList<ViewPagerItemMainPage> viewPagerItemMainPageArrayList) {
        this.viewPagerItemMainPageArrayList = viewPagerItemMainPageArrayList;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewpageritemmainpage, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPagerItemMainPage viewPagerItemMainPage = viewPagerItemMainPageArrayList.get(position);


        String[] names = new String[4];
        names[0] = viewPagerItemMainPage.name_1;
        names[1] = viewPagerItemMainPage.name_2;
        names[2] = viewPagerItemMainPage.name_3;
        names[3] = viewPagerItemMainPage.name_4;
        int[] thtime_hours = {
                8, 9, 11, 13, 13, 15, 17, 18, 20
        };
        int[] thtime_min = {
                15, 55, 35, 15, 45, 25, 5, 50, 30
        };
        String[] timedef_dermo = {
                "8.15", "9.55","11.35","13.15", "13.45", "15.25", "17.05","18.50", "20.30"
        };
        int _limitchars = 5;

        List<String> teachersList = Arrays.asList("Агеева Ольга Андреевна", "Адамова Гюльнара Амучиевна", "Адмиралова Ирина Александровна", "Азоев Геннадий Лазаревич", "Азоева Ольга Валентиновна", "Айрапетян Мамикон Сергеевич", "Акимов Олег Михайлович", "Акопян Анна Рубеновна", "Алешина Ирина Викторовна", "Алешникова Вера Ивановна", "Амелина Елена Михайловна", "Аминов Давид Исакович", "Ананьина Любовь Геннадьевна", "Андрианова Раиса Игоревна", "Аникеева Лидия Васильевна", "Аникин Борис Александрович", "Аникин Олег Борисович", "Антипова Екатерина Сергеевна", "Антонов Виктор Глебович", "Антонов Сергей Александрович", "Антонова Ольга Михайловна", "Антропова Марина Юрьевна", "Аракелова Галина Александровна", "Аракелян Артур Мовсесович", "Асеев Александр Дмитриевич", "Астафьева Ольга Евгеньевна", "Атоян Снежана Васильевна", "Атурин Валерий Викторович", "Афанасьев Валентин Яковлевич", "Ахмаева Людмила Геннадьевна", "Ашурбеков Рафик Ашурбекович", "Бадьина Анна Викторовна", "Байкова Оксана Викторовна", "Бакрадзе Андрей Анатольевич", "Баранцев Сергей Анатольевич", "Бардина Ирина Валентиновна", "Башкина Наталья Андреевна", "Беликова Диана Вячеславовна", "Белова Елена Юрьевна", "Белова Ольга Львовна", "Белоусов Виталий Андреевич", "Белоусова Мария Николаевна", "Беляев Андрей Михайлович", "Береговская Татьяна Александровна", "Бирюков Александр Петрович", "Близкий Роман Сергеевич", "Блинникова Алла Викторовна", "Блинова Наталья Владимировна", "Бобылева Нина Васильевна", "Богданов Сергей Васильевич", "Богданова Марина Валерьевна", "Богданова Татьяна Владимировна", "Болдырева Людмила Борисовна", "Большакова Ольга Ильинична", "Бондарович Екатерина Петровна", "Борисова Виктория Владимировна", "Борисова Татьяна Павловна", "Ботавина Елена Борисовна", "Бренц Виктория Игоревна", "Брижак Ольга Валентиновна", "Брикошина Ирина Станиславовна", "Бурмистрова Лариса Алексеевна", "Бутковская Галина Вальдеровна", "Бухтоярова Марина Васильевна", "Быковская Екатерина Николаевна", "Бычкова Нина Сергеевна", "Бычкова Светлана Георгиевна", "Васильева Ксения Валерьевна", "Васильева Ольга Анатольевна", "Ведищева Татьяна Александровна", "Верниченко Марат Александрович", "Вишняков Яков Дмитриевич", "Владимирова Алла Федоровна", "Войко Александр Вячеславович", "Войко Дарья Викторовна", "Володина Светлана Вячеславовна", "Волох Владимир Александрович", "Волошин Владимир Иванович", "Воронцов Виктор Борисович", "Воронцова Юлия Владимировна", "Выходцева Елена Анатольевна", "Гаганова  Елена Владимировна", "Гайдамашко Игорь Вячеславович", "Галичкина Марина Александровна", "Гарник Сергей Валентинович", "Гатауллин Тимур Малютович", "Гибадуллин Артур Артурович", "Гизатулина Инна Вячеславовна", "Глазьев Сергей Юрьевич", "Годин Владимир Викторович", "Голышкова Ирина Николаевна", "Гончаров Игорь Леонидович", "Гончаров Михаил Владимирович", "Горелова Ольга Александровна", "Горин Виктор Сергеевич", "Горобец Виктор Дмитриевич", "Гоц Евгения Валентиновна", "Грецова Лилия Михайловна", "Григорян Марина Игоревна", "Грицаев Сергей Анатольевич", "Гришаева Светлана Алексеевна", "Гришин Валентин Николаевич", "Грищенко Леонид Леонидович", "Громова Ольга Николаевна", "Губарева ­Елена Алексеевна", "Гулиев ­Азамат Юсуфович", "Гулькова Елена Леонидовна", "Гуляева Эльвира Фаритовна", "Гуреев Павел Михайлович", "Гурова Екатерина Викторовна", "Гусева Евгения Николаевна", "Гусева Мария Николаевна", "Давлетшина Лейсан Анваровна", "Даитгаджиев Гамадали Магомедович", "Данилина Ольга Михайловна", "Дашков Андрей Александрович", "Дегтярёва Виктория Владимировна", "Дементьев Виктор Евгеньевич", "Демин Александр Васильевич", "Демкина Ольга Витальевна", "Демьянец Владимир Николаевич", "Денисова Анна Игоревна", "Денисова Анна Леонидовна", "Деревягина Людмила Николаевна", "Дианина Елена Вадимовна", "Диденко Валерий Дмитриевич", "Добрышина Людмила Николаевна", "Довжик Валерий Николаевич", "Довжик Галина Владимировна", "Долгих Екатерина Алексеевна", "Долгополов Дмитрий Владиславович", "Домащенко Валерий Сергеевич", "Дорошенко Ольга Марковна", "Дроботенко Олег Николаевич", "Дудник Леонид Викторович", "Дуненкова Елена Николаевна", "Дьяконова Мария Александровна", "Дьяконова Ольга Семеновна", "Евреинова Елена Александровна", "Елькина Дарья Андреевна", "Ельчанинофф Лариса Игоревна", "Еремеева Анастасия Игоревна", "Еремина Татьяна Николаевна", "Ерзнкян  Баграт  Айкович", "Ермаков Иван Александрович", "Ершов Анатолий Тихонович", "Ершова Наталья Борисовна", "Ефанова Людмила Дмитриевна", "Ефимова Виктория Викторовна", "Ефимова Марина Владимировна", "Ефимова Марина Романовна", "Жернакова Марина Борисовна", "Жилкина Анна Николаевна", "Жуков Вадим Анатольевич", "Жукова Марина Александровна", "Журавлева Ольга Вячеславовна", "Забелина Елена Павловна", "Зайцев Алексей Викторович", "Зайцева Валентина Николаевна", "Замятина Наталья Александровна", "Захаров Дмитрий Кириллович", "Захаров Михаил Юрьевич", "Захарова Александра Вячеславовна", "Збарская Анна Владимировна", "Зеленцова Лидия Сергеевна", "Знаменский Дмитрий Юрьевич", "Зозуля Антон Валериевич", "Зозуля Павел Валерьевич", "Зотов Владимир Борисович", "Зубарева Елена Вячеславовна", "Зубач Анатолий Васильевич", "Ибрагимов Фархад Эльшан оглы", "Ибятов Фаиль Мужипович", "Иванов Владимир Константинович", "Иванов Игорь Николаевич", "Иванова Валерия Ивановна", "Ивановская Людмила Владимировна", "Ивановский Василий Серафимович", "Ивина Карина Владимировна", "Игнатова Яна Сергеевна", "Ильюхов Александр Антонович", "Имаева Елена Зайнетдиновна", "Ионцева Мария Владимировна", "Кабанова Ирина Александровна", "Казанова Елена Валериевна", "Казанцева Наталья Васильевна", "Казбан Елена Петровна", "Казеева Ольга Гельевна", "Кайдалова Татьяна Анатольевна", "Какаева Евгения Александровна", "Камара Патрис", "Камчатова Екатерина Юрьевна", "Кандыбович Сергей Львович", "Канке Алла Анатольевна", "Канунникова  Анна Михайловна", "Карп Марина Викторовна", "Карсанова Елена Созрикоевна", "Касаткина Ангелина Александровна", "Каталкина Мария Юрьевна", "Кафиятуллина Юлия Насиховна", "Качалов Сергей Борисович", "Качалова Татьяна Анатольевна", "Каштанова Екатерина Викторовна", "Кириллов Виктор Николаевич", "Кирка Артём Викторович", "Кирова Елена Александровна", "Киселева Светлана Петровна", "Кисленко Николай Анатольевич", "Клейнер Георгий Борисович", "Клым-Еремина Наталья Владимировна", "Ковалев Михаил Николаевич", "Ковалева Ирина Алексеевна", "Коготкова Ирина Захаровна", "Кожевникова Лариса Валерьевна", "Козлов Антон Гордеевич", "Козлова Анастасия Алексеевна", "Козловский Александр Васильевич", "Кокорева Наталья Валерьевна", "Колобкова Валентина Алексеевна", "Колосова Ольга Анатольевна", "Колчин Андрей Александрович", "Комарова Анна Алексеевна", "Конкс Виктор Янович", "Конобевцев Федор Дмитриевич", "Коновалова Валерия Германовна", "Константинова Любовь Андреевна", "Коренко Юлия Михайловна", "Корзоватых Жанна Михайловна", "Кормишова Аида Васильевна", "Коробкина Анна Николаевна", "Королева Нонна Шараповна", "Косарин Сергей Петрович", "Косачев Дмитрий Андреевич", "Косинова Марина Ивановна", "Костенников Михаил Валерьевич", "Костикова Юлия Валерьевна", "Костриков Сергей Петрович", "Костриков Станислав Сергеевич", "Кравченко Олег Владимирович", "Крамаренко Инна Владимировна", "Краснов Евгений Владимирович", "Красовский Юрий Дмитриевич", "Криволапчук Игорь Альлерович", "Кротенко Татьяна Юрьевна", "Крылов Александр Николаевич", "Крылов Владимир Константинович", "Крылова Наталья Федоровна", "Крысов Виктор Владимирович", "Крыштановская Ольга Викторовна", "Крюкова Елена Вячеславовна", "Ксенофонтов Андрей Александрович", "Кудрина Екатерина Леонидовна", "Кузина Галина Петровна", "Кузнецов Николай Владимирович", "Кузьмин Виталий Васильевич", "Кузьмина Елена Юрьевна", "Кузьминова Татьяна Владиславовна", "Кузьминых Светлана Сергеевна", "Кулигин Василий Дмитриевич", "Куликова Ольга Андреевна", "Кулясова Елена Васильевна", "Купцова Елена Валентиновна", "Куракин Алексей Валентинович", "Курасова Ольга Вячеславовна", "Курбатова Анна Владимировна", "Кутернин Михаил Иванович", "Лаас Наталья Ивановна", "Лавров Иван Андреевич", "Лапочкина Анна Александровна", "Лаптев Леонид Григорьевич", "Лапшина Наталия Викторовна", "Ларина Ольга Игоревна", "Ласточкина Галина Александровна", "Латфуллин Габдельахат Рашидович", "Лебедева Юлия Аркадьевна", "Левина Лариса Федоровна", "Леншин Сергей Иванович", "Леонова Татьяна Николаевна", "Линник Владимир Юрьевич", "Линник Юрий Николаевич", "Липатов Андрей Геннадьевич", "Липченкова Юлия Эдуардовна", "Лобачев Виталий Владимирович", "Лобачёва Анастасия Сергеевна", "Ловчева Марина Владимировна", "Логачев Никита Вячеславович", "Логачева Валентина Васильевна", "Логинов Борис Борисович", "Лукьянов Сергей Александрович", "Любимова Наталия Геннадьевна", "Любимцева Людмила Петровна", "Лялин Алексей Михайлович", "Маевский Владимир Иванович", "Мазурина Татьяна Юрьевна", "Майорова Елена Ивановна", "Макаренко Анна Евгеньевна", "Макеева Виктория Геннадиевна", "Максимов Джангар Константинович", "Малышкин Николай Геннадьевич", "Мальцева Мария Валерьевна", "Мамышев Евгений Васильевич", "Мантров Юрий Николаевич", "Маркин Валерий Васильевич", "Масленников Илья Анатольевич", "Матвеева Надежда Сергеевна", "Матяш Татьяна Михайловна", "Махалин Виктор Николаевич", "Махалина Оксана Михайловна", "Мацкуляк Иван Дмитриевич", "Медведева Ольга Евгеньевна", "Межевов Александр Дмитриевич", "Меренков Артем Олегович", "Мецгер Андрей Александрович", "Милькина Ирина Владимировна", "Митрофанова Александра Евгеньевна", "Митрофанова Елена Александровна", "Михайленко Михаил Николаевич", "Михалевич Лариса Юрьевна", "Михалевич Наталья Викторовна", "Мишин Юрий Владимирович", "Мовсисян Ара Тигранович", "Мога Ирина Сергеевна", "Мозгачева Анна Станиславовна", "Моисеенко Наталья Анатольевна", "Мокий Михаил Стефанович", "Морозова Анна Валериевна", "Морозова Ирина Михайловна", "Морозова Надежда Григорьевна", "Морыженкова Наталья Владимировна", "Мохова Галина Викторовна", "Мусатова Сима Андреевна", "Мустафин Тимур Абдулхалимович", "Мышко Федор Георгиевич", "Мышко Юлия Андреевна", "Надехина Юлия Петровна", "Найденова Елена Георгиевна", "Нгуен Куанг Тхыонг", "Небесная Елена Владимировна", "Незамайкин Иван Валерьевич", "Неопуло Клавдия Лазаревна", "Никитин Сергей Александрович", "Никитина Елена Владимировна", "Никулин Алексей Сергеевич", "Нольде Евгений Львович", "Образцов Александр Викторович", "Овчиева Юлия Александровна", "Оздоева Эльза Ахметовна", "Окольнишникова Ирина Юрьевна", "Олимпиев Анатолий Юрьевич", "Омельченко Николай Алексеевич", "Онищенко Светлана Ивановна", "Опекунов Валерий Александрович", "Опокин Алексей Борисович", "Орешина Марина Николаевна", "Орлова Екатерина Анатольевна", "Орлова Любовь Васильевна", "Орлова Марина Викторовна", "Остапенко Владислав Анатольевич", "Павловский Павел Владимирович", "Павлюк Екатерина Сергеевна", "Павлюк Лариса Владимировна", "Панин Валерий Иванович", "Панова Екатерина Андреевна", "Панфилова Елена Евгеньевна", "Паршикова Галина Юрьевна", "Паршинцева Лидия Сергеевна", "Пасько Александр Владимирович", "Персианов Владимир Александрович", "Перфильев Алексей Анатольевич", "Першина Татьяна Алексеевна", "Петренко Борис Васильевич", "Петрина Ольга Анатольевна", "Пигасова Ирина Викторовна", "Писарева Ольга Михайловна", "Питрюк Анастасия Валерьевна", "Показаньев Владислав Юрьевич", "Полякова Валентина Владимировна", "Полякова Елена Васильевна", "Попов Владимир Владимирович", "Поспелов Сергей Валерьевич", "Почекутов Максим Павлович", "Притолюк Алина Викторовна", "Прохоров Юрий Георгиевич", "Прохорова Инна Сергеевна", "Пузанова Ирина Алексеевна", "Пустохин Денис Александрович", "Пустохина Ирина Валерьевна", "Путиловская Татьяна Сергеевна", "Раева Ольга Георгиевна", "Раевский Дмитрий Александрович", "Райченко Александр Васильевич", "Ревзон Оксана Анатольевна", "Рогуленко Татьяна Михайловна", "Родина Ирина Борисовна", "Родионов Антон Николаевич", "Рожкова Надежда Константиновна", "Романов Алексей Александрович", "Романова Ирина Анатольевна", "Руднев Александр Васильевич", "Рукавишников Сергей Михайлович", "Румянцев Валерий Павлович", "Румянцева Ирина Анатольевна", "Рыбина Марина Николаевна", "Рыжова Юлия Игоревна", "Рыльская Марина Александровна", "Рязанова Галина Николаевна", "Савин Алексей Викторович", "Савостин Алексей Анатольевич", "Савостицкий Артем Сергеевич", "Савченко Алексей Владимирович", "Савченко Дмитрий Игоревич", "Савченко-Бельский Владимир Юрьевич", "Сазанова Светлана Леонидовна", "Сакульева Татьяна Николаевна", "Салтыковский Вадим Артурович", "Салынская Татьяна Владимировна", "Самоделко Людмила Сергеевна", "Самосудов Михаил Владимирович", "Саралдаева Татьяна Павловна", "Светкина Татьяна Федоровна", "Свистунов Василий Михайлович", "Семенов Игорь Владиславович", "Серебрякова Галина Валентиновна", "Серёгина Вера Александровна", "Серов Виктор Михайлович", "Серова Светлана Юрьевна", "Сиверкина Татьяна Евгеньевна", "Сизова Светлана Владимировна", "Силаев Александр Александрович", "Силина Светлана Александровна", "Симина Татьяна Евгеньевна", "Сираждинов Рифат Жамалетдинович", "Скосырева Анна Сергеевна", "Смагулова Самал Мураденовна", "Смирнов Евгений Николаевич", "Смирнова Валентина Григорьевна", "Смирнова Татьяна Вячеславовна", "Снеговской", "Сергей Иванович", "Соболевская Ольга Владимировна", "Соболь Оксана Владимировна", "Соколов Николай Николаевич", "Соколов Юрий Владимирович", "Соколова Светлана Викторовна", "Соколовская Ирина Эдуардовна", "Солдатова Наталья Федоровна", "Солнцева Оксана Глебовна", "Сорокина Галина Петровна", "Сороко Григорий Янович", "Стадолин Михаил Евгеньевич", "Старовойтова Ирина Евгеньевна", "Старостин Василий Сергеевич", "Статкус Александр Владимирович", "Степанов Алексей Алексеевич", "Стрельникова Ирина Александровна", "Сувалова Татьяна Викторовна", "Суворова Вера Александровна", "Сумарокова Екатерина Викторовна", "Сумбатян Сона Левоновна", "Сундукова Галина Михайловна", "Супоницкий Владислав Львович", "Сухарева Наталья Александровна", "Суязова Светлана Андреевна", "Сычев Андрей Алексеевич", "Сычёва Светлана Михайловна", "Таболин Владимир Викторович", "Талалова Лариса Николаевна", "Тарарин Игорь Михайлович", "Твердола Наталья Михайловна", "Темнышов Игорь Анатольевич", "Теплякова Марина Юрьевна", "Терехова Анна Евгеньевна", "Терехова Нина Васильевна", "Тетцоева Ольга Алексеевна", "Тилов Арсен Асланбекович", "Тимофеев Олег Анатольевич", "Тимофеева Татьяна Борисовна", "Тимохович Александра Николаевна", "Тинякова Виктория Ивановна", "Типалина Мария Валерьевна", "Титов Сергей Анатольевич", "Титова Наталья Викторовна", "Титор Светлана Евгеньевна", "Тихонов Юрий Петрович", "Токарев Борис Евгеньевич", "Токун Людмила Валентиновна", "Толкунова Мария Сергеевна", "Томилина Елена Евгеньевна", "Топунова Ирина Романовна", "Траченко Марина Борисовна", "Туровский Александр Алексеевич", "Тучкова Ирина Геннадьевна", "Убушаева Байрта Григорьевна", "Урунов Асрор Алижонович", "Учирова Маргарита Юрьевна", "Фаюстов Анатолий Афанасьевич", "Федотова Татьяна Владимировна", "Филимонов Дмитрий Александрович", "Филиндаш Лариса Васильевна", "Фирсова Светлана Владимировна", "Флаксман Алина Сергеевна", "Фомин Павел Алексеевич", "Фролова Елена Андреевна", "Хабиб Марина Далхатовна", "Халимон Екатерина Андреевна", "Харчилава Гоча Патаевич", "Хмель Александр Анатольевич", "Хмельченко Елена Геннадьевна", "Хожанова Ирина Александровна", "Хрипунова Анна Сергеевна", "Хромов Сергей Евгеньевич", "Цаболова Ольга Руслановна", "Цунаева Юлия Олеговна", "Чеботарева Зоя Валентиновна", "Черепов Виктор Михайлович", "Черкасова Марина Александровна", "Чернавин Юрий Александрович", "Чернов Сергей Борисович", "Чернова Вероника Юрьевна", "Чернова Светлана Георгиевна", "Чернова Татьяна Федоровна", "Чичерин Вадим Петрович", "Чудновский Алексей Данилович", "Чурзина Елена Юрьевна", "Шагиева Розалина Васильевна", "Шаламова Наталия Гавриловна", "Шаманина Елизавета Ивановна", "Шананин Николай Алексеевич", "Шаповал Елена Валентиновна", "Шапошников Святослав Вячеславович", "Шарипов Фанис Фалихович", "Шевченко Марина Олеговна", "Шемякина Татьяна Юрьевна", "Шим Галина Андреевна", "Шишкова Анастасия Викторовна", "Шкаровский Сергей Иванович", "Шнырева Елена Аркадьевна", "Шрамченко Тамара Борисовна", "Шураева Лариса Юрьевна", "Щербакова Ирина Константиновна", "Щербина Анатолий Иванович", "Эриашвили Нодари Дарчоевич", "Юровицкий Сергей Яковлевич", "Яковлев Александр Юрьевич", "Якунина Галина Евгеньевна", "Ямчук Елена Васильевна", "Янковская Вероника Владимировна", "Ярощук Анатолий Борисович", "Ясницкая Арина Анатольевна", "Яхина Ирина Юрьевна", "Яхшиян Олег Юрьевич", "Яценко Оксана Юрьевна" );

        //System.out.println(teachersList);


        for (int i = 0; i < 4; i++) {
            if (names[i] != null) {
                if(names[i].split("x")[2].length() != 4)
                    holder.tcdate.setText(names[i].split("x")[2]);



                if (names[i].split("x")[1].length() < _limitchars)
                    holder._tcblocks[i].setVisibility(View.GONE);
                else {

                    try {
                        holder._tc_th_times[i].setText(names[i].split("x")[3]);
                        if (holder._tc_th_times[i].getText().toString().equals("") || holder._tc_th_times[i].getText().toString().equals(" ")) {
                            holder._tc_th_times[i].setVisibility(View.GONE);
                        }
                    } catch (Exception e) {

                    }




                    String s = names[i].split("x")[0];
                    s = s.replaceAll("[- ]", "-");
                    if (s.contains("---"))
                        s = s.replace("--", "");
                    else if (s.contains("--"))
                        s = s.replaceFirst("-", "");
                    holder._tctimes[i].setText(s);

//
                    /*new_name_new представляет собой строку имени прошедшую проверку на конченое наименование
                    типа "Физическая культура / Языки и методы программирования" и, если оно таково, переписывает ее
                     в нормальный вид*/

                    String new_name_new = (names[i].split("x")[1]);

                    if ((names[i].split("x")[1]).contains("Актовый зал /")) {
                        new_name_new = new_name_new.substring(new_name_new.indexOf("/") + 1);
                        new_name_new.trim();
                    }
                    else if ((names[i].split("x")[1]).contains("Актовый зал")) {
                        new_name_new = new_name_new.substring(new_name_new.indexOf("зал") + 3);
                        new_name_new.trim();
                    }



                    System.out.println(i);
                    System.out.println(names[i].split("x")[1]);


                    /*String strung = "the text=text";
                    String s1 = strung.substring(strung.indexOf("=") + 1);
                    s1.trim();


*/
                    //decomposition(names[i].split("x")[1])[0] - вывод предметов
                    //decomposition(names[i].split("x")[1])[1] - вывод типа пары
                    //decomposition(names[i].split("x")[1])[2] - должно выводить имена, но выводит говно в том числе

                    holder._tcnames[i].setText(decomposition(new_name_new)[0].replaceAll("[\n]", ""));
                    //holder._tcnames[i].setText(decomposition(names[i].split("x")[1])[0].replaceAll("[\n]", ""));

                    if (decomposition(names[i].split("x")[1])[1].contains("Лекция")) {
                        holder._tctypes[i].setBackgroundResource(R.drawable.background_lesson_type_red);
                        holder._tctypes[i].setText("Лекция");

                    }
                    else if (decomposition(names[i].split("x")[1])[1].contains("Практическое занятие")) {
                        holder._tctypes[i].setBackgroundResource(R.drawable.background_lesson_type_blue);
                        holder._tctypes[i].setText("Практика");

                    }
                    else if (decomposition(names[i].split("x")[1])[1].contains("Лабораторное занятие")) {
                        holder._tctypes[i].setBackgroundResource(R.drawable.background_lesson_type_purple);
                        holder._tctypes[i].setText("Лабораторная");

                    }

                    else {
                        holder._tctypes[i].setText("Проект");
                        holder._tctypes[i].setBackgroundResource(R.drawable.background_lesson_type_pink);
                    }

                    /* new_name представляет собой строку, проверяющую нет ли пробела или еще какой-нибудь дичи в начале строки имени
                    * и если что-то такое есть, то переписывает ее в нормальный вид */
                    if (decomposition(names[i].split("x")[1])[2].contains("нед")) {
                        String new_name = names[i].substring(names[i].lastIndexOf(")") + 1);
                        String replace_name = names[i].substring(names[i].lastIndexOf(".") + 1);
                        new_name = new_name.replace(replace_name, "");
                        new_name = new_name.replace("\n", "");
                        String checkFirstLetter = String.valueOf(new_name.charAt(0));
                        if (checkFirstLetter.equals(" ")) {
                            new_name = new_name.replaceFirst(" ", "");
                        }
//                        holder._tcteachers[i].setText(new_name);
                    }

                    /*if (decomposition(names[i].split("x")[1])[2].matches(".*\\d.*"))
                        holder._tcteachers[i].setVisibility(View.GONE);*/



                    /*else
                        holder._tcteachers[i].setText(decomposition(new_name_new)[2]);*/

                    /*if (holder._tcteachers[i].getText().toString().contains("Л")
                            || holder._tcteachers[i].getText().toString().contains("нед")
                            || holder._tcteachers[i].getText().toString().matches(".*\\d.*")) {
                        holder._tcteachers[i].setVisibility(View.GONE);
                    }*/

                    ///Экономика и управление инв. крашит при просмотре четной недели, а именно пн и чт
                    //char firstCharOf_tcrooms = decomposition(names[i].split("x")[1])[3].charAt(0);

                    //Если изменить, всё рабоатет вроде. Зачем это?
                    String firstCharOf_tcrooms = decomposition(names[i].split("x")[1])[3];


                    if (decomposition(names[i].split("x")[1])[3].contains("Спортивный комплекс")) {
                        holder._tcrooms[i].setText("СК");
                    } else if ((firstCharOf_tcrooms + "").equals("-")) {
                        holder._tcrooms[i].setText(decomposition(names[i].split("x")[1])[3].substring(1));
                    }
                    else holder._tcrooms[i].setText(decomposition(names[i].split("x")[1])[3]);

                    holder._tcblocks[i].setVisibility(View.VISIBLE);
                    holder._tctypes[i].setVisibility(View.VISIBLE);
                    holder._tcteachers[i].setVisibility(View.VISIBLE);
                    holder._tcrooms[i].setVisibility(View.VISIBLE);

                    String nameField;
                    final String[] checkFullName = {"SHORT"};

                    for (int k = 0; k < teachersList.size(); k++) {

                        String arr[] = teachersList.get(k).split(" ");
                        System.out.println(arr[0]);
                        if (names[i].split("x")[1].contains(arr[0].trim())) {
                            nameField = arr[0] + " " + arr[1].charAt(0) + ". " + arr[2].charAt(0) + ".";
                            holder._tcteachers[i].setText(nameField);
                        } /*else
                            holder._tcteachers[i].setVisibility(View.GONE);*/
                    }

                    if (holder._tcteachers[i].getText().toString().contains("Преподователь"))
                        holder._tcteachers[i].setVisibility(View.GONE);

                    int finalI = i;
                    holder._tcteachers[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int k = 0; k < teachersList.size(); k++) {
                                String arr[] = teachersList.get(k).split(" ");
                                if (names[finalI].split("x")[1].contains(arr[0].trim())) {
                                    if (checkFullName[0].equals("SHORT")) {
                                        holder._tcteachers[finalI].setText(teachersList.get(k));
                                        checkFullName[0] = "LONG";
                                    } else {
                                        holder._tcteachers[finalI].setText(arr[0] + " " + arr[1].charAt(0) + ". " + arr[2].charAt(0) + ".");
                                        checkFullName[0] = "SHORT";
                                    }
                                }
                            }

                        }
                    });

                    /*if (decomposition(names[i].split("x")[1])[1].length() < 3)
                        holder._tctypes[i].setVisibility(View.GONE);*/
                    /*if (decomposition(names[i].split("x")[1])[2].length() < 3)
                        holder._tcteachers[i].setVisibility(View.GONE);*/
                    if (decomposition(names[i].split("x")[1])[3].length() < 3)
                        holder._tcrooms[i].setVisibility(View.GONE);


                }
            } else
                holder._tcblocks[i].setVisibility(View.GONE);

        }



        new CountDownTimer(3600000, 5000){
            Integer _timepar = 0;

            @Override

            public void onTick(long l) {
                Date date = new Date();
                for(int i = 0;i < 4; i++)
                {
                    for(int t=0;t<9;t++)
                    {
                        if(names[i].contains(timedef_dermo[t]))
                            _timepar = (thtime_hours[t] * 60) + thtime_min[t];

                    }


                    Integer difftime_inmin = _timepar - ((date.getHours() * 60) + date.getMinutes());
                    String tmp_th = "Через " + (difftime_inmin / 60) + "ч. " + (difftime_inmin%60) + "м.";
                    Integer abs_difftime_inmin = Math.abs(difftime_inmin);
                    if(difftime_inmin > 0) {
                        if(difftime_inmin < 60)
                            tmp_th = "Через "+ (difftime_inmin%60) + "м.";
                        if(difftime_inmin % 60 == 0)
                            tmp_th = "Через " + (difftime_inmin / 60) + "ч.";
                        if(difftime_inmin <= 10) {
                            holder._tc_th_times[i].setTextColor(Color.RED);
                        }

                    }
                        else {
                            if (abs_difftime_inmin <= 90) {
                                tmp_th = "Идет " + (abs_difftime_inmin / 60) + "ч. " + (abs_difftime_inmin % 60) + "м.";
                                holder._tc_th_times[i].setTextColor(Color.parseColor("#000000"));
                                if (abs_difftime_inmin < 60)
                                    tmp_th = "Идет " + (abs_difftime_inmin % 60) + "м.";
                                if (abs_difftime_inmin % 60 == 0)
                                    tmp_th = "Идет " + (abs_difftime_inmin / 60) + "ч.";
                            }
                            if (abs_difftime_inmin > 90) {
                                tmp_th = " ";
                            }
                        }

                    holder._tc_th_times[i].setText("" + tmp_th);
                        if (holder._tc_th_times[i].getText().toString().equals("") || holder._tc_th_times[i].getText().toString().equals(" ")) {
                            holder._tc_th_times[i].setVisibility(View.GONE);
                        }

                    }


                }


            @Override
            public void onFinish() {

                start();
            }
        }.start();



    }





    @Override
    public int getItemCount() {
        return viewPagerItemMainPageArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView[] _tctimes = new TextView[4];
        TextView[] _tc_th_times = new TextView[4];
        TextView[] _tcnames = new TextView[4];
        TextView[] _tctypes = new TextView[4];
        TextView[] _tcteachers = new TextView[4];
        TextView[] _tcrooms = new TextView[4];
        ConstraintLayout[] _tcblocks = new ConstraintLayout[4];
        TextView tcdate;

        //TextView[] borders = new TextView[4];


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tcdate = itemView.findViewById(R.id.Date);
            _tc_th_times[0] = itemView.findViewById(R.id.th_Time_1);
            _tc_th_times[1] = itemView.findViewById(R.id.th_Time_2);
            _tc_th_times[2] = itemView.findViewById(R.id.th_Time_3);
            _tc_th_times[3] = itemView.findViewById(R.id.th_Time_4);
            _tcblocks[0] = itemView.findViewById(R.id.block_1);
            _tcblocks[1] = itemView.findViewById(R.id.block_2);
            _tcblocks[2] = itemView.findViewById(R.id.block_3);
            _tcblocks[3] = itemView.findViewById(R.id.block_4);
            _tctimes[0] = itemView.findViewById(R.id.Time_1);
            _tctimes[1] = itemView.findViewById(R.id.Time_2);
            _tctimes[2] = itemView.findViewById(R.id.Time_3);
            _tctimes[3] = itemView.findViewById(R.id.Time_4);
            _tcnames[0] = itemView.findViewById(R.id.Name);
            _tcnames[1] = itemView.findViewById(R.id.Name_2);
            _tcnames[2] = itemView.findViewById(R.id.Name_3);
            _tcnames[3] = itemView.findViewById(R.id.Name_4);
            _tctypes[0] = itemView.findViewById(R.id.Type_1);
            _tctypes[1] = itemView.findViewById(R.id.Type_2);
            _tctypes[2] = itemView.findViewById(R.id.Type_3);
            _tctypes[3] = itemView.findViewById(R.id.Type_4);
            _tcteachers[0] = itemView.findViewById(R.id.Teacher_1);
            _tcteachers[1] = itemView.findViewById(R.id.Teacher_2);
            _tcteachers[2] = itemView.findViewById(R.id.Teacher_3);
            _tcteachers[3] = itemView.findViewById(R.id.Teacher_4);
            _tcrooms[0] = itemView.findViewById(R.id.Room_1);
            _tcrooms[1] = itemView.findViewById(R.id.Room_2);
            _tcrooms[2] = itemView.findViewById(R.id.Room_3);
            _tcrooms[3] = itemView.findViewById(R.id.Room_4);



//            borders[0] = itemView.findViewById(R.id.border_1);
//            borders[1] = itemView.findViewById(R.id.border_2);
//            borders[2] = itemView.findViewById(R.id.border_3);
//            borders[3] = itemView.findViewById(R.id.border_4);

        }


    }


    private String[] decomposition(String SPLIT) {

        String[] tempstring = new String[4];
        /*
         *  tempstring[0] - Название
         *  tempstring[1] - Тип
         *  tempstring[2] - Препод
         *  tempstring[3] - Аудитория
         */
        if (SPLIT.contains("(ЛЗ"))
            tempstring[1] = "Лабораторное занятие";
        else if (SPLIT.contains("(ПЗ"))
            tempstring[1] = "Практическое занятие";
        else if (SPLIT.contains("(Л "))
            tempstring[1] = "Лекция";
        else
            tempstring[1] = "";

        for (int le = 0; le < SPLIT.length(); le++) {
            if (SPLIT.charAt(le) == '(') {
                tempstring[0] = SPLIT.substring(0, le - 1);
                break;
            }
        }

        int tempsc = 0;

        for (int le = 0; le < SPLIT.length(); le++) {
            if (SPLIT.charAt(le) == ')') {
                tempsc = le + 2;
                break;
            }
        }
        tempstring[3] = "";
        tempstring[2] = "";
        try {

            if (SPLIT.contains("ЛК-")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 6, SPLIT.length());
                tempstring[2] = SPLIT.substring(tempsc, SPLIT.length() - 7);
            } else if (SPLIT.contains("У-") || SPLIT.contains("А-") || SPLIT.contains("ПА-")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 5, SPLIT.length());
                tempstring[2] = SPLIT.substring(tempsc, SPLIT.length() - 6);
            } else if (SPLIT.contains("этаж")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 9, SPLIT.length());

            } else if (SPLIT.contains("Спортивный комплекс")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 19, SPLIT.length());
            } else
                tempstring[3] = "";


            if (tempstring[2].contains("\n")) {
                tempstring[2] = tempstring[2].substring(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return tempstring;
    }

}
