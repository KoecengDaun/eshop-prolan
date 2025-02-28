# Refleksi 1
## Refleksi Kode

- **Clean Code:**
  - Aplikasi ini telah menerapkan arsitektur MVC dengan baik, memisahkan tanggung jawab antara controller, service, dan repository sehingga kode menjadi lebih modular dan mudah dipahami.
  - Penamaan kelas, metode, dan variabel dirancang dengan jelas dan deskriptif, memudahkan pengembang lain dalam memahami maksud dan fungsi dari tiap bagian kode.
  - Prinsip DRY (Don't Repeat Yourself) diterapkan secara konsisten, misalnya dengan penggunaan metode `findById` yang menyederhanakan logika pencarian produk di seluruh aplikasi.

- **Secure Coding:**
  - Validasi input telah diterapkan untuk mencegah potensi serangan injeksi, sehingga meningkatkan integritas dan keamanan data.
  - Terdapat mekanisme konfirmasi sebelum melakukan aksi destruktif seperti penghapusan produk, yang membantu mencegah kesalahan pengguna.
  - Penanganan error diarahkan dengan baik, misalnya dengan mengarahkan pengguna kembali ke halaman yang aman jika terjadi kesalahan atau data tidak ditemukan.

- **Area untuk Peningkatan:**
  - Implementasi custom exceptions dapat ditambahkan untuk memberikan penanganan error yang lebih spesifik dan mudah dalam proses debugging.
  - Validasi sisi server melalui Bean Validation (seperti penggunaan anotasi `@NotNull` dan `@Size`) bisa meningkatkan keandalan aplikasi.
  - Integrasi logging menggunakan SLF4J dan Logback serta penerapan Spring Security pada endpoint-endpoint sensitif akan menambah lapisan keamanan tambahan.
  - Pada sisi testing, refactoring kode test agar mengurangi duplikasi (misalnya dengan mengekstraksi setup yang sama ke kelas dasar) akan meningkatkan maintainability dan kebersihan kode.

# Refleksi 2
## Refleksi Unit Testing

Setelah menyelesaikan penulisan unit test, saya merasa lega dan puas karena test-test tersebut berhasil memverifikasi bahwa fungsi-fungsi dalam aplikasi berjalan sesuai dengan ekspektasi, baik untuk skenario positif maupun negatif. Saya menyadari bahwa jumlah unit test dalam sebuah kelas tidak harus banyak, melainkan cukup untuk mencakup setiap fungsionalitas penting—termasuk kasus-kasus batas (edge cases) yang mungkin muncul. Setiap metode publik sebaiknya memiliki unit test yang menguji berbagai skenario untuk memastikan kebenaran logika. Untuk memastikan bahwa unit test yang saya buat cukup memverifikasi program, saya menggunakan metrik code coverage. Meskipun memiliki 100% code coverage memberikan gambaran bahwa seluruh kode telah diuji, saya juga menyadari bahwa itu tidak menjamin kode bebas dari bug karena test yang ada belum tentu menguji semua interaksi kompleks atau kasus tak terduga.

## Refleksi Functional Testing

Setelah menyelesaikan `CreateProductFunctionalTest.java` yang mensimulasikan interaksi pengguna untuk menambahkan produk, saya diminta membuat functional test suite baru yang memverifikasi jumlah item dalam daftar produk. Dalam proses tersebut, saya menyadari bahwa ada banyak bagian setup dan variabel instance yang sama dengan functional test sebelumnya. Duplikasi kode seperti ini, jika tidak diatasi, bisa menurunkan kualitas kode dan menyulitkan pemeliharaan di masa depan. Untuk meningkatkan kebersihan kode, saya menyarankan agar bagian-bagian setup yang sama diekstraksi ke dalam kelas dasar (base class) atau utilitas bersama. Dengan begitu, setiap functional test suite dapat mewarisi atau menggunakan fungsi-fungsi umum tersebut, sehingga mengurangi duplikasi dan meningkatkan keterbacaan serta maintainability kode. Secara keseluruhan, meskipun unit test dan functional test sangat penting untuk memastikan kualitas aplikasi, menjaga agar kode test itu sendiri tetap bersih dan terstruktur adalah hal yang tidak kalah penting, sehingga kita bisa terus menambah test tanpa mengorbankan kualitas dan kemudahan perawatan kode.

# Refleksi 3

## Code Quality Issues and How I Fixed Them
- **Duplicate Literal Strings:** Sonar menandai string `"redirect:list"` yang dipakai berulang kali di `ProductController`. Saya memperbaikinya dengan mendefinisikan sebuah constant `private static final String REDIRECT_LIST = "redirect:list";` dan menggunakan constant tersebut di semua tempat yang membutuhkan.
- **Anchor Tags as Buttons:** Pada file HTML, saya mengganti anchor yang berfungsi sebagai tombol `Delete` dengan `<form>` + `<button>` agar sesuai dengan semantik HTML dan best practice aksesibilitas.
- **Thread.sleep Usage:** Di salah satu functional test, saya mengganti `Thread.sleep()` dengan `WebDriverWait` dari Selenium agar lebih stabil dan tidak bergantung pada penundaan statis.
- **Empty Methods:** Beberapa metode kosong seperti `contextLoads()` atau `setUp()` membutuhkan komentar penjelasan. Saya menambahkan komentar untuk memberitahukan bahwa metode tersebut memang sengaja dibiarkan kosong.

## Continuous Integration and Continuous Deployment
Saya yakin implementasi saat ini sudah memenuhi definisi Continuous Integration dan Continuous Deployment. Setiap kali ada perubahan kode (push) ke repository, pipeline secara otomatis menjalankan serangkaian tes (unit test, functional test) dan melakukan code analysis (misalnya dengan SonarCloud). Setelah itu, pipeline juga melakukan deployment otomatis ke platform PaaS yang saya pilih (misalnya Render atau Koyeb). Dengan demikian, setiap perubahan dicek secara konsisten, kualitas kode terpantau, dan versi terbaru aplikasi langsung tersedia di lingkungan produksi tanpa intervensi manual.

# Refleksi 4

## 1) Prinsip yang diterapkan pada proyek
Dalam proyek ini, saya menerapkan kelima prinsip SOLID, yaitu:
1. **Single Responsibility Principle (SRP)**:  
   Setiap kelas atau modul memiliki satu tanggung jawab spesifik. Sebagai contoh, `CarService` hanya bertugas mengelola logika bisnis mobil, sedangkan `CarRepository` hanya menangani penyimpanan data.
2. **Open/Closed Principle (OCP)**:  
   Kode dirancang agar mudah diperluas tanpa perlu memodifikasi kode yang sudah stabil. Contohnya, jika ingin menambahkan validasi baru saat membuat mobil, cukup menambah metode di `CarService` tanpa merombak keseluruhan struktur.
3. **Liskov Substitution Principle (LSP)**:  
   Sub-kelas atau implementasi harus bisa menggantikan super-kelas atau interface-nya tanpa mengubah perilaku yang diharapkan. Di sini, misalnya, `CarServiceImpl` dapat digunakan di mana pun `CarService` dibutuhkan, tanpa mengganggu program.
4. **Interface Segregation Principle (ISP)**:  
   Interface dibuat khusus untuk kebutuhan tertentu sehingga kelas tidak dipaksa mengimplementasikan metode yang tidak relevan. Misalnya, `CarService` berisi metode spesifik untuk mobil, `ProductService` berisi metode khusus untuk produk.
5. **Dependency Inversion Principle (DIP)**:  
   Modul tingkat tinggi bergantung pada abstraksi (interface), bukan pada implementasi konkret. Contohnya, `CarServiceImpl` bergantung pada `CarRepository` (interface) sehingga mudah mengganti implementasi repository tanpa mengubah kode layanan.

## 2) Keuntungan menerapkan prinsip SOLID (dengan contoh)
- **Mudah Dikembangkan (Contoh: OCP)**  
  Ketika perlu menambahkan fitur atau metode baru, tidak perlu mengubah keseluruhan sistem. Misalnya, menambahkan logika validasi baru untuk `Car` cukup ditambahkan di `CarServiceImpl` tanpa menyentuh `CarRepository`.
- **Mudah Dites (Contoh: DIP)**  
  Dengan constructor injection yang bergantung pada interface, bisa mengganti `CarRepository` asli dengan versi *mock* saat melakukan unit test. Ini membuat pengujian lebih mudah dan terisolasi.
- **Perawatan Kode Lebih Sederhana (Contoh: SRP)**  
  Karena setiap kelas memiliki tanggung jawab tunggal, perubahan atau penelusuran bug lebih cepat dilakukan. Contohnya, jika ada kesalahan pada pengelolaan data, cukup memeriksa `CarRepository` ketimbang menelusuri kode di banyak tempat.

## 3) Kerugian jika tidak menerapkan prinsip SOLID (dengan contoh)
- **Kode Sulit Dikembangkan (Contoh: Pelanggaran OCP)**  
  Jika satu kelas melakukan banyak hal sekaligus, menambahkan fitur baru bisa memaksa mengubah kode dasar, berpotensi menimbulkan bug di berbagai bagian lain.
- **Kode Sulit Diuji (Contoh: Pelanggaran DIP)**  
  Ketika sebuah kelas langsung bergantung pada implementasi konkret, harus mengatur *setup* yang kompleks untuk tes. Akibatnya, timbul kesulitan dan memakan banyak waktu saat melakukan unit test.
- **Perawatan Mahal dan Rumit (Contoh: Pelanggaran SRP)**  
  Kelas yang menampung tanggung jawab terlalu banyak (misalnya, logic bisnis + akses data + manipulasi UI) akan membuat perbaikan bug atau perubahan kecil berdampak luas ke keseluruhan kode, sehingga memakan waktu dan biaya.