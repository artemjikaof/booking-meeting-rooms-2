# Система бронирования помещений — Android (Kotlin)

## Стек технологий (по ТЗ)
- **Язык:** Kotlin
- **IDE:** Android Studio
- **Архитектура:** MVVM + Jetpack
- **БД:** Room (локальное хранение)
- **DI:** Hilt
- **Навигация:** Navigation Component
- **Фоновая синхронизация:** WorkManager
- **Изображения:** Glide
- **Календарь UI:** kizitonwose/calendar-view
- **Календарь системный:** Android Calendar Provider API

## Структура проекта

```
app/src/main/java/com/example/roombooking/
├── RoomBookingApp.kt                       # Application + WorkManager config
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt                 # Room database
│   │   ├── Entities.kt                    # RoomEntity, EventEntity, Converters
│   │   └── Daos.kt                        # RoomDao, EventDao, EventWithRoom
│   └── repository/
│       ├── CalendarSyncManager.kt         # Calendar Provider API wrapper
│       ├── Repositories.kt                # RoomRepository, EventRepository + Mappers
│       └── SyncPreferences.kt             # SharedPreferences wrapper
├── di/
│   └── DatabaseModule.kt                  # Hilt DI: DB, DAOs
├── domain/model/
│   └── Models.kt                          # Room, Event, SyncConflict
├── presentation/
│   ├── MainActivity.kt                    # NavHostFragment + BottomNavigation
│   ├── calendar/
│   │   ├── CalendarFragment.kt            # Главный экран — календарь
│   │   ├── CalendarViewModel.kt           # Логика выбора дат, режимов просмотра
│   │   └── EventsAdapter.kt               # RecyclerView для событий
│   ├── events/
│   │   ├── AddEditEventViewModel.kt        # Сохранение, валидация, проверка конфликтов
│   │   └── AddEditEventFragment.kt        # Форма добавления/редактирования
│   ├── rooms/
│   │   └── RoomsViewModel.kt              # CRUD помещений
│   └── settings/
│       ├── SettingsViewModel.kt            # Управление синхронизацией
│       └── SettingsFragment.kt            # UI настроек + диалоги конфликтов
└── util/
    ├── CalendarSyncWorker.kt              # WorkManager: фоновая синхронизация (4-6ч)
    └── BootReceiver.kt                    # Восстановление WorkManager после перезагрузки
```
 
## Реализованные требования ТЗ

### Функциональные требования
- [x] Главная страница — календарь (месяц / неделя / день)
- [x] Визуальное выделение прошедших / текущих / предстоящих мероприятий
- [x] CRUD помещений (с проверкой привязанных мероприятий при удалении)
- [x] CRUD мероприятий с привязкой к помещению
- [x] Проверка конфликта бронирования (двойное бронирование)
- [x] Фильтрация мероприятий по дате / помещению / названию
- [x] Интеграция с Calendar Provider API (READ/WRITE_CALENDAR)
- [x] Запрос разрешений с пояснением при первом запуске
- [x] Добавление событий в системный календарь при создании
- [x] Двусторонняя синхронизация (изменения ↔ Calendar Provider)
- [x] Фильтрация импортируемых событий по меткам (#Бронирование)
- [x] Отображение событий из системного календаря с пометкой
- [x] Диалог разрешения конфликтов синхронизации (3 варианта)
- [x] Настройки синхронизации (вкл/выкл, выбор календаря, метки)
- [x] Кнопка «Синхронизировать сейчас» + статус
- [x] Фоновая синхронизация каждые 4 часа (WorkManager)
- [x] Восстановление после перезагрузки (BootReceiver)

### Технические требования
- [x] Платформа: Android (minSdk 26)
- [x] Kotlin
- [x] Room Database (локальное хранение)
- [x] MVVM архитектура + Jetpack (ViewModel, LiveData/Flow, Navigation)
- [x] Hilt (DI)
- [x] WorkManager (фоновая синхронизация)
- [x] Glide (загрузка изображений помещений)
- [x] Calendar Provider API
- [x] READ_CALENDAR / WRITE_CALENDAR разрешения
- [x] Material Design 3

## Запуск
1. Открыть в Android Studio Hedgehog или новее
2. Sync Gradle
3. Run на устройстве с Android 8.0+ (API 26+)

## Следующие шаги
- Добавить layout-файлы для оставшихся фрагментов (rooms, settings, detail)
- Реализовать PhotoPicker для фото помещений
- Добавить поддержку Firebase для онлайн-версии (опционально по ТЗ)
