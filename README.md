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

testing